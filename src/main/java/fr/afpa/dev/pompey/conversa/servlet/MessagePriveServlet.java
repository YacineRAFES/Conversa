package fr.afpa.dev.pompey.conversa.servlet;

import fr.afpa.dev.pompey.conversa.utilitaires.Page;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(name = "MessagePriveServlet", value = "/messageprive")
public class MessagePriveServlet extends HttpServlet {

    @Override
    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// Définir le titre de la page
        request.setAttribute("title", "Messages Privés");
        // Définir le nom du fichier JavaScript à inclure
        request.setAttribute("js", "messagesprivee.js");
        this.getServletContext().getRequestDispatcher(Page.JSP.MESSAGES_PRIVEE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String messagePrive = request.getParameter("message");
        log.info(messagePrive);

        // Préparer une réponse JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"status\":\"ok\"}");
    }

    @Override
    public void destroy() {

    }
}