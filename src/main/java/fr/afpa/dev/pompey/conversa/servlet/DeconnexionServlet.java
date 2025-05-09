package fr.afpa.dev.pompey.conversa.servlet;

import fr.afpa.dev.pompey.conversa.utilitaires.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

import static fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils.deleteCookies;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.sendRedirectTo;

@WebServlet(name = "DeconnexionServlet", value = "/deconnexion")
public class DeconnexionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("title", "Déconnexion");
        // Invalider la session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        deleteCookies(request, response);

        // Rediriger l'utilisateur vers la page d'accueil
        sendRedirectTo(request, response, Utils.ServletPage.LOGIN);
    }
}
