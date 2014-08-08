package net.unit8.sessionista.encoder;

import net.unit8.sessionista.EncodeException;
import net.unit8.sessionista.StateEncoder;

import java.io.*;

/**
 * @author kawasima
 */
public class JavaSerializeStateEncoder implements StateEncoder {
    @Override
    public <T> byte[] encode(T obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            return baos.toByteArray();
        } catch(IOException e) {
            throw new EncodeException(e);
        }
    }

    @Override
    public <T> T decode(byte[] dmp, Class<T> type) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(dmp);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (Exception e) {
            throw new EncodeException(e);
        }
    }

}
