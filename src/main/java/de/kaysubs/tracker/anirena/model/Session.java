package de.kaysubs.tracker.anirena.model;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.List;

public class Session {
    private final static String COOKIE_SID = "anirena_com_sid";
    private final static String COOKIE_U = "anirena_com_u";
    private final static String COOKIE_K = "anirena_com_k";

    private final String sid;
    private final String k;
    private final String u;

    /**
     * Some urls return multiple cookies with the same
     * name where only the last one is the correct one.
     */
    private static String selectLastCookie(List<Cookie> cookies, String name) {
        Cookie cookie = null;

        for(Cookie c : cookies)
            if(c.getName().equals(name))
                cookie = c;

        if(cookie == null)
            throw new IllegalArgumentException("Expected cookie \"" + name + "\"");
        else
            return cookie.getValue();
    }

    public Session(List<Cookie> cookies) {
        this.sid = selectLastCookie(cookies, COOKIE_SID);
        this.k = selectLastCookie(cookies, COOKIE_K);
        this.u = selectLastCookie(cookies, COOKIE_U);
    }

    public Session(String sid, String k, String u) {
        this.sid = sid;
        this.k = k;
        this.u = u;
    }

    public String getSid() {
        return sid;
    }

    public String getK() {
        return k;
    }

    public String getU() {
        return u;
    }

    public Cookie[] toCookies() {
        BasicClientCookie[] cookies = new BasicClientCookie[] {
                new BasicClientCookie(COOKIE_U, u),
                new BasicClientCookie(COOKIE_K, k),
                new BasicClientCookie(COOKIE_SID, sid)
        };

        for(BasicClientCookie cookie : cookies) {
            cookie.setDomain("www.anirena.com");
            cookie.setPath("/");
        }

        return cookies;
    }
}
