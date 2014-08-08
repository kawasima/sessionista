package net.unit8.sessionista;

import java.io.Serializable;

/**
 * @author kawasima
 */
public class SessionEntry implements Serializable {
    private String key;
    private Object value;
    private SessionStore storage;

    public SessionEntry(String key, Object value, SessionStore storage) {
        this.key = key;
        this.value = value;
        this.storage = storage;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public SessionStore getStorage() {
        return storage;
    }
}
