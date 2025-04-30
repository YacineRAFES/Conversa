package fr.afpa.dev.pompey.conversa.utilitaires;

import jakarta.json.JsonObject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class CookiesUtils {

    private static final String JWT = "jwt";
    private static final String USERID = "userId";
    private static final String USERNAME = "username";

    static List<String> lesCookiesAsupprimer = List.of(
            JWT,
            USERID,
            USERNAME
    );

    public static void createCookies(HttpServletRequest request, HttpServletResponse response, Map<String, Object> apiResponse) {
        log.info("Fonction createCookies enclenchée");

        JsonObject jsonObject = (JsonObject) apiResponse.get("json");
        String autorisation = (String) apiResponse.get("Authorization");
        JsonObject user = (JsonObject) jsonObject.get("user");
        String userId = user.getString("iduser");
        String username = user.getString("username");

        String token = autorisation.replace("Bearer ", "");
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        Cookie usernameCookie = new Cookie("username", username);
        Cookie userIdCookie = new Cookie("userId", userId);

        response.addCookie(cookie);
        response.addCookie(usernameCookie);
        response.addCookie(userIdCookie);

    }

    public static String getJWT(HttpServletRequest request) {
        // Supprimer le cookie JWT
        Cookie[] cookies = request.getCookies();
        log.info("Fonction getJWT enclenchée");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (JWT.equals(cookie.getName())) {
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
                if (USERID.equals(cookie.getName())) {
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
                if (USERNAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void deleteCookies(HttpServletRequest request, HttpServletResponse response) {
        log.info("Fonction deleteCookies enclenchée");
        // Supprimer le cookie JWT
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (lesCookiesAsupprimer.contains(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);  // Expire immédiatement
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);  // Si le cookie était HttpOnly
                    cookie.setSecure(true);  // Si le cookie était sécurisé (sur HTTPS)
                    response.addCookie(cookie);
                }
            }
        }
    }


}
