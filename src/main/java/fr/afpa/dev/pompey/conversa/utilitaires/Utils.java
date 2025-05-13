package fr.afpa.dev.pompey.conversa.utilitaires;

import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils.deleteCookies;

@Slf4j
public class Utils {
    public static String getNameClass() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        // Recherche de la première classe hors de Utils
        for (int i = 2; i < stackTrace.length; i++) {
            String nomClasse = stackTrace[i].getClassName();
            if (!nomClasse.equals(Utils.class.getName())) {
                String nomMethode = stackTrace[i].getMethodName();
                int numLigne = stackTrace[i].getLineNumber();
                return "Appelé depuis : " + nomClasse + "." + nomMethode + " (ligne " + numLigne + ")";
            }
        }

        return "Emplacement appelant non trouvé";
    }

    public static void backToPageLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("backToPageLogin {}", Utils.getNameClass());
        log.info("Redirection vers login");
        GoToPage(request, response, ServletPage.DECONNEXION);
        // Invalider la session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        deleteCookies(request, response);

        // Rediriger l'utilisateur vers la page d'accueil
        response.sendRedirect(request.getContextPath() + "/");
    }

    public enum ServletPage {
        AMIS,
        ADMIN,
        ACCMANAGEMENT,
        HOME,
        LOGIN,
        MESSAGEPRIVE,
        REGISTER,
        DECONNEXION
    }

    public static void GoToPage(HttpServletRequest request, HttpServletResponse response, ServletPage page, String roles) throws ServletException, IOException {
        switch(page){
            case LOGIN:
                request.setAttribute("title", "Connexion");
                request.setAttribute("js", "connexion.js");
                request.getServletContext().getRequestDispatcher(Page.JSP.LOGIN).forward(request, response);
                break;
            case REGISTER:
                request.setAttribute("title", "Inscription");
                request.setAttribute("js", "register.js");
                request.getServletContext().getRequestDispatcher(Page.JSP.REGISTER).forward(request, response);
                break;
            case HOME:
                request.setAttribute("title", "Home");
                request.setAttribute("js", "home.js");

                if ("admin".equalsIgnoreCase(roles)) {
                    request.setAttribute("menu", "admin");
                } else {
                    request.setAttribute("menu", "clients");
                }
                request.getServletContext().getRequestDispatcher(Page.JSP.HOME).forward(request, response);
                break;
            case AMIS:
                request.setAttribute("title", "Amis");
                request.setAttribute("js", "amis.js");

                if ("admin".equalsIgnoreCase(roles)) {
                    request.setAttribute("menu", "admin");
                } else {
                    request.setAttribute("menu", "clients");
                }
                request.getServletContext().getRequestDispatcher(Page.JSP.AMIS).forward(request, response);
                break;
            case MESSAGEPRIVE:
                request.setAttribute("title", "Messages Privés");
                request.setAttribute("js", "messagesprivee.js");

                if ("admin".equalsIgnoreCase(roles)) {
                    request.setAttribute("menu", "admin");
                } else {
                    request.setAttribute("menu", "clients");
                }
                request.getServletContext().getRequestDispatcher(Page.JSP.MESSAGES_PRIVEE).forward(request, response);
                break;
            case ADMIN:
                request.setAttribute("title", "Admin");
                request.setAttribute("js", "admin.js");

                if ("admin".equalsIgnoreCase(roles)) {
                    request.setAttribute("menu", "admin");
                }
                request.getServletContext().getRequestDispatcher(Page.JSP.ADMIN).forward(request, response);
                break;
            case ACCMANAGEMENT:
                request.setAttribute("title", "Gestion des comptes");
                request.setAttribute("js", "accmanagement.js");

                if ("admin".equalsIgnoreCase(roles)) {
                    request.setAttribute("menu", "admin");
                }
                request.getServletContext().getRequestDispatcher(Page.JSP.ACCMANAGEMENT).forward(request, response);
                break;
            default:
                log.error("Erreur dans GoToPage, on trouve pas la page demandé");
        }
    }

    public static void GoToPage(HttpServletRequest request, HttpServletResponse response, ServletPage page) throws ServletException, IOException {
        GoToPage(request, response, page, "clients");
    }

    public static void sendRedirectTo(HttpServletRequest request, HttpServletResponse response, ServletPage page) throws IOException {
        switch (page) {
            case LOGIN:
                response.sendRedirect(request.getContextPath() + "/login");
                break;
            case REGISTER:
                response.sendRedirect(request.getContextPath() + "/register");
                break;
            case HOME:
                response.sendRedirect(request.getContextPath() + "/home");
                break;
            case AMIS:
                response.sendRedirect(request.getContextPath() + "/amis");
                break;
            case MESSAGEPRIVE:
                response.sendRedirect(request.getContextPath() + "/messageprive");
                break;
            case ADMIN:
                response.sendRedirect(request.getContextPath() + "/admin");
                break;
        }
    }

    public static Map<String, Object> jsonObjectToMap(JsonObject jsonObject){
        Map<String, Object> map = new HashMap<>();
        for(String key : jsonObject.keySet()){
            JsonValue value = jsonObject.get(key);
            switch (value.getValueType()) {
                case STRING:
                    map.put(key, ((JsonString) value).getString());
                    break;
                case NUMBER:
                    map.put(key, ((JsonNumber) value).intValue());
                    break;
                case TRUE, FALSE:
                    map.put(key, Boolean.valueOf(value.toString()));
                    break;
                default:
                    map.put(key, value.toString());
            }
        }
        return map;
    }
}
