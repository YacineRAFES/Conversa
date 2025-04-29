package fr.afpa.dev.pompey.conversa.utilitaires;

import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CookiesUtils {
    public static String getJWT(Cookie[] cookies) {
        log.info("Fonction getJWT enclenchée");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String getIdUser(Cookie[] cookies) {
        log.info("Fonction getIdUser enclenchée");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String getUsername(Cookie[] cookies) {
        log.info("Fonction getUsername enclenchée");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
