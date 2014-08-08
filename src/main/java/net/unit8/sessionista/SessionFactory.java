package net.unit8.sessionista;

import net.unit8.sessionista.encoder.JavaSerializeStateEncoder;

import java.util.List;

/**
 * @author kawasima
 */
public class SessionFactory {
    private List<SessionStore> availableStores;
    private StateEncoder defaultEncoder;

    public SessionFactory() {
        defaultEncoder = new JavaSerializeStateEncoder();
    }

    public Session create() {
        Session session = new Session();
        session.setSessionFactory(this);
        return session;
    }

    public void setAvailableStores(List<SessionStore> sessionStores) {
        this.availableStores = sessionStores;
        for(SessionStore store : sessionStores) {
            if (store.getStateEncoder() == null) {
                store.setStateEncoder(defaultEncoder);
            }
        }
    }

    public List<SessionStore> getAvailableStores() {
        return availableStores;
    }

    public StateEncoder getDefaultEncoder() {
        return defaultEncoder;
    }

    public void setDefaultEncoder(StateEncoder defaultEncoder) {
        this.defaultEncoder = defaultEncoder;
    }
}
