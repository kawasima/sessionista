package net.unit8.sessionista;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author kawasima
 */
public abstract class SessionStore {
    private String name;
    private Long expiresMilliSeconds;
    private boolean extendable = true;
    private StateEncoder stateEncoder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpires(Long expires, TimeUnit timeUnit) {
        this.expiresMilliSeconds = timeUnit.toMillis(expires);
    }

    public abstract List<SessionEntry> load(UUID sessionId);
    public abstract void save(UUID sessionId, List<SessionEntry> entries);

    protected byte[] encode(List<SessionEntry> entries) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            for(SessionEntry entry : entries) {
                dos.writeUTF(entry.getKey());
                Object obj = entry.getValue();
                if (obj == null) {
                    dos.writeInt(0);
                } else {
                    byte[] encoded = stateEncoder.encode(obj);
                    dos.writeInt(encoded.length);
                    dos.writeUTF(obj.getClass().getCanonicalName());
                    dos.write(encoded);
                }
            }
        } catch(IOException e) {
            throw new EncodeException(e);
        }
        return baos.toByteArray();
    }

    protected List<SessionEntry> decode(byte[] encoded) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(encoded));
        List<SessionEntry> entries = new ArrayList<SessionEntry>();
        try {
            while(dis.available() > 0) {
                String key = dis.readUTF();
                int length = dis.readInt();
                if (length == 0) {
                    entries.add(new SessionEntry(key, null, this));
                } else {
                    String typeName = dis.readUTF();
                    byte[] buf = new byte[length];
                    dis.read(buf);
                    Class<?> type = Class.forName(typeName);
                    entries.add(new SessionEntry(key, stateEncoder.decode(buf, type), this));
                }
            }
            return entries;
        } catch(IOException e) {
            throw new EncodeException(e);
        } catch(ClassNotFoundException e) {
            throw new EncodeException(e);
        }
    }

    public long getExpiresMilliSeconds() {
        return expiresMilliSeconds;
    }

    public boolean isExtendable() {
        return extendable;
    }

    public StateEncoder getStateEncoder() {
        return stateEncoder;
    }

    public void setStateEncoder(StateEncoder stateEncoder) {
        this.stateEncoder = stateEncoder;
    }
}
