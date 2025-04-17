package fr.afpa.dev.pompey.conversa.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "DeconnexionServlet", value = "/deconnexion")
public class DeconnexionServlet extends HttpServlet {

    private static final String COOKIE_NAME = "jwt";  // Assure-toi que le nom du cookie JWT est correct

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalider la session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Supprimer le cookie JWT
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
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
