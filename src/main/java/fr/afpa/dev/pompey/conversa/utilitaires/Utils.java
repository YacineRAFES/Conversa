package fr.afpa.dev.pompey.conversa.utilitaires;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

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

    public static void backToPageLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("backToPageLogin " + Utils.getNameClass());
        definirPage(request, ServletPage.DECONNEXION);
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
        HOME,
        LOGIN,
        MESSAGEPRIVE,
        REGISTER,
        DECONNEXION
    }

    public static void definirPage(HttpServletRequest request , ServletPage page, String roles){
        switch(page){
            case LOGIN:
                request.setAttribute("title", "Connexion");
                request.setAttribute("js", "connexion.js");
                break;
            case REGISTER:
                request.setAttribute("title", "Inscription");
                request.setAttribute("js", "register.js");
                break;
            case HOME:
                request.setAttribute("title", "Home");
                request.setAttribute("js", "home.js");

                if ("admin".equalsIgnoreCase(roles)) {
                    request.setAttribute("menu", "admin");
                } else {
                    request.setAttribute("menu", "clients");
                }
                break;
            case AMIS:
                request.setAttribute("title", "Amis");
                request.setAttribute("js", "amis.js");

                if ("admin".equalsIgnoreCase(roles)) {
                    request.setAttribute("menu", "admin");
                } else {
                    request.setAttribute("menu", "clients");
                }
                break;
            case MESSAGEPRIVE:
                request.setAttribute("title", "Messages Privés");
                request.setAttribute("js", "messagesprivee.js");

                if ("admin".equalsIgnoreCase(roles)) {
                    request.setAttribute("menu", "admin");
                } else {
                    request.setAttribute("menu", "clients");
                }
                break;
            case ADMIN:
                request.setAttribute("title", "Admin");
                request.setAttribute("js", "admin.js");

                if ("admin".equalsIgnoreCase(roles)) {
                    request.setAttribute("menu", "admin");
                }
                break;
        }
    }

    public static void definirPage(HttpServletRequest request, ServletPage page) {
        definirPage(request, page, "clients");
    }
}
