package fr.afpa.dev.pompey.conversa.servlet;
import fr.afpa.dev.pompey.conversa.securite.Captcha;
import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.Page;
import jakarta.json.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.*;

@Slf4j
@WebServlet(name = "LoginServlet", value = {"/login", "/"})
public class LoginServlet extends HttpServlet {
    private static final String MESSAGE = "message";
    private static final String SET_DIV_ERROR = "setDivError";

    @Override
    public void init() {
    
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Définir le titre de la page
        request.setAttribute("title", "Connexion");

        this.getServletContext().getRequestDispatcher(Page.LOGIN).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String captcha = request.getParameter("cf-turnstile-response");

        log.info("Captcha : " + captcha);

        boolean isCaptchaValid = Captcha.verif(captcha);
        if (!isCaptchaValid) {
            log.error("Captcha invalide");
            response.sendRedirect(request.getContextPath() + "/login?info=erreurCaptcha");
            return;
        }
        Map<String, String> formData = new HashMap<>();
        formData.put("email", request.getParameter("email"));
        formData.put("password", request.getParameter("password"));

        JsonObject jsonObject = envoyerFormulaireVersApi(formData, LOGIN);
        if (jsonObject.getString("status").equals("success")) {
            request.setAttribute("title", "Accueil");
            log.info("Inscription réussie et redirection vers la page de connexion");
            this.getServletContext().getRequestDispatcher(Page.HOME).forward(request, response);
        } else if (jsonObject.getString("status").equals("error")) {
            request.setAttribute("title", "Connexion");
            log.info("INVALIDCREDENTIALS");
            request.setAttribute(SET_DIV_ERROR, Alert.INVALIDCREDENTIALS); // Afficher le message d'erreur
            this.getServletContext().getRequestDispatcher(Page.LOGIN).forward(request, response); // Rediriger vers la page d'inscription
        }else{

        }
    }
    
    @Override
    public void destroy() {
    
    }
}