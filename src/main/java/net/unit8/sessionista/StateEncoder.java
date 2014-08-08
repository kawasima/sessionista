package net.unit8.sessionista;

/**
 * @author kawasima
 */
public interface StateEncoder {
    public <T> byte[] encode(T obj);
    public <T> T decode(byte[] dmp, Class<T> type);
}
