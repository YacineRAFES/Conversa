package fr.afpa.dev.pompey.conversa.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeconnexionServlet", value = "/deconnexion")
public class DeconnexionServlet extends HttpServlet {

    private static final String JWT = "jwt";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("title", "Déconnexion");
        // Invalider la session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        List<String> lesCookiesAsupprimer = List.of(
                JWT
        );

        // Supprimer le cookie JWT
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (lesCookiesAsupprimer.contains(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);  // Expire immédiatement
                    cookie.setPath("/");  // Assurez-vous que le chemin correspond à celui défini lors de la création du cookie
                    cookie.setHttpOnly(true);  // Si le cookie était HttpOnly
                    cookie.setSecure(true);  // Si le cookie était sécurisé (sur HTTPS)
                    response.addCookie(cookie);
                    break;  // Sortir de la boucle après avoir supprimé le cookie
                }
            }
        }

        // Rediriger l'utilisateur vers la page d'accueil
        response.sendRedirect(request.getContextPath() + "/");
    }
}
