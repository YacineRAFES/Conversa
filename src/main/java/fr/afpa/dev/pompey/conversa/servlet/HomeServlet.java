package fr.afpa.dev.pompey.conversa.servlet;
import fr.afpa.dev.pompey.conversa.utilitaires.Page;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {

    @Override
    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Définir le titre de la page
        request.setAttribute("title", "Home");
        // Définir le nom du fichier JavaScript à inclure
        request.setAttribute("js", "home.js");

        this.getServletContext().getRequestDispatcher(Page.JSP.HOME).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    
    @Override
    public void destroy() {
    
    }
}