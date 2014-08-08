package net.unit8.sessionista;

import net.unit8.sessionista.encoder.JavaSerializeStateEncoder;

import java.io.Serializable;
import java.util.*;

/**
 * @author kawasima
 */
public class Session implements Serializable {
    private UUID sessionId;
    private SessionFactory factory;

    private final Map<String, SessionEntry> entryMap = new HashMap<String, SessionEntry>();
    private static final StateEncoder defaultStateEncoder = new JavaSerializeStateEncoder();

    public Session() {
    }

    public UUID getId() {
        return sessionId;
    }

    public void load(UUID sessionId) {
        for (SessionStore store : factory.getAvailableStores()) {
            List<SessionEntry> entries = store.load(sessionId);
            for (SessionEntry entry : entries) {
                entryMap.put(entry.getKey(), entry);
            }
        }
    }

    public void save() {
        Map<SessionStore, List<SessionEntry>> entriesByStorage = new HashMap<SessionStore, List<SessionEntry>>();
        for (SessionEntry entry : entryMap.values()) {
            List<SessionEntry> entries = entriesByStorage.get(entry.getStorage());
            if (entries == null) {
                entries = new ArrayList<SessionEntry>();
                entriesByStorage.put(entry.getStorage(), entries);
            }
            entries.add(entry);
        }

        for (Map.Entry<SessionStore, List<SessionEntry>> e : entriesByStorage.entrySet()) {
            e.getKey().save(sessionId, e.getValue());
        }
    }

    public Object get(String key) {
        SessionEntry entry = entryMap.get(key);
        if (entry == null)
            return null;
        return entry.getValue();
    }

    public <T> T get(String key, Class<?> type) {
        Object value = get(key);
        return (T) value;
    }

    public void put(String key, Object value, String storeName) {
        for (SessionStore storage : factory.getAvailableStores()) {
            if (storage.getName().equals(storeName)) {
                entryMap.put(key, new SessionEntry(key, value, storage));
                return;
            }
        }
    }

    public void delete(String key) {
        entryMap.remove(key);
    }

    public void deleteAll() {
        entryMap.clear();
    }

    protected void setSessionFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public SessionFactory getSessionFactory() {
        return factory;
    }
}
