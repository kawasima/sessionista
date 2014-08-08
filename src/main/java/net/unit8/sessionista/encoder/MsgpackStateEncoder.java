package net.unit8.sessionista.encoder;

import net.unit8.sessionista.EncodeException;
import net.unit8.sessionista.StateEncoder;
import org.msgpack.MessagePack;

import java.io.IOException;

/**
 * @author kawasima
 */
public class MsgpackStateEncoder implements StateEncoder {
    private MessagePack messagePack;

    public MsgpackStateEncoder() {
        messagePack = new MessagePack();
    }

    @Override
    public <T> byte[] encode(T obj) {
        try {
            return messagePack.write(obj);
        } catch (IOException e) {
            throw new EncodeException(e);
        }
    }

    @Override
    public <T> T decode(byte[] dmp, Class<T> type) {
        try {
            return messagePack.read(dmp, type);
        } catch (IOException e) {
            throw new EncodeException(e);
        }
    }

    public void register(Class<?>... classes) {
        for(Class<?> c : classes) {
            messagePack.register(c);
        }
    }
}
