package fr.afpa.dev.pompey.conversa.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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
        request.setAttribute("js", "messagesprive.js");
        this.getServletContext().getRequestDispatcher("/JSP/page/clients/messagesPrivate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void destroy() {

    }
}