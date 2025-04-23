package fr.afpa.dev.pompey.conversa.utilitaires;

import jakarta.servlet.http.Cookie;

public class CookiesUtils {
    public static String getJWT(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
