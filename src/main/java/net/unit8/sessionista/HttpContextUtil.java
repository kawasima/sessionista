package net.unit8.sessionista;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kawasima
 */
public class HttpContextUtil {
    private static ThreadLocal<HttpServletRequest> requestLocal;

    public static void setRequest(HttpServletRequest request) {
        requestLocal.set(request);
    }

    public static HttpServletRequest getRequest() {
        return requestLocal.get();
    }
}
