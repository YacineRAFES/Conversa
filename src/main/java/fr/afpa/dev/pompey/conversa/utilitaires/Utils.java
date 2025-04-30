package fr.afpa.dev.pompey.conversa.utilitaires;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils.deleteCookies;

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
        request.setAttribute("title", "Déconnexion");
        // Invalider la session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        deleteCookies(request, response);

        // Rediriger l'utilisateur vers la page d'accueil
        response.sendRedirect(request.getContextPath() + "/");
    }
}
