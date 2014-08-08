package net.unit8.sessionista.id;

import net.unit8.sessionista.Session;
import net.unit8.sessionista.SessionStore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author kawasima
 */
public class CookieIdPersistenter {
    private String cookieName = "SESSIONISTA_ID";
    public void write(Session session, HttpServletResponse response) {
        long maxAge = 0L;
        for (SessionStore store : session.getSessionFactory().getAvailableStores()) {
            if (store.isExtendable()) {
                maxAge = Math.max(maxAge, store.getExpiresMilliSeconds());
            }
        }
        Cookie cookie = new Cookie(cookieName, session.getId().toString());
        cookie.setMaxAge((int)(maxAge / 1000));
        response.addCookie(cookie);
    }
    public UUID read(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    try {
                        return UUID.fromString(cookie.getValue());
                    } catch(Exception cheatCookie) {
                        break;
                    }
                }
            }
        }
        return null;
    }
}
