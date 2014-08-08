package net.unit8.sessionista.store;

import net.unit8.sessionista.HttpContextUtil;
import net.unit8.sessionista.SessionEntry;
import net.unit8.sessionista.SessionStore;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

/**
 * @author kawasima
 */
public class HttpSessionStore extends SessionStore {
    @Override
    public List<SessionEntry> load(UUID sessionId) {
        HttpSession httpSession = HttpContextUtil.getRequest().getSession();

        List<SessionEntry> entries = new ArrayList<SessionEntry>();
        Enumeration names = httpSession.getAttributeNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement().toString();
            entries.add(new SessionEntry(key, httpSession.getAttribute(key), this));
        }
        return entries;
    }

    @Override
    public void save(UUID sessionId, List<SessionEntry> entries) {
        HttpSession httpSession = HttpContextUtil.getRequest().getSession();

        for (SessionEntry entry : entries) {
            httpSession.setAttribute(entry.getKey(), entry.getValue());
        }
    }
}
