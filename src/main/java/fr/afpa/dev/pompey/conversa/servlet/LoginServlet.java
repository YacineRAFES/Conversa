package fr.afpa.dev.pompey.conversa.servlet;
import fr.afpa.dev.pompey.conversa.securite.Captcha;
import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils;
import fr.afpa.dev.pompey.conversa.utilitaires.Page;
import fr.afpa.dev.pompey.conversa.utilitaires.Utils;
import jakarta.json.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils.deleteCookies;
import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.*;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.GoToPage;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.ServletPage.ADMIN;

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
        deleteCookies(request, response);

        // request qui contient tout les informations envoyées par le client lors de la requête HTTP vers le serveur
        // response qui permet d'envoyer une réponse HTTP au client
        // Définir le titre de la page
        request.setAttribute("title", "Connexion");

        GoToPage(request, response, Utils.ServletPage.LOGIN);
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

        // 29/04/2025 j'ai mis Map<String, String> formData = new HashMap<>(); en Map<String, Object> formData = new HashMap<>();
        Map<String, Object> formData = new HashMap<>();
        formData.put("email", request.getParameter("email"));
        formData.put("password", request.getParameter("password"));

        Map<String, Object> apiResponse = envoyerFormulaireVersApi(formData, LOGIN);

        if(apiResponse != null){

            JsonObject jsonObject = (JsonObject) apiResponse.get("json");

            if (jsonObject.getString("status").equals("success")) {

                CookiesUtils.createCookies(request, response, apiResponse);

                log.info("Connexion réussie et redirection vers la page d'accueil");

                response.sendRedirect(request.getContextPath() + "/home");

            } else if (jsonObject.getString("status").equals("error")) {

                request.setAttribute("title", "Connexion");

                if(jsonObject.getString(MESSAGE).equals("InvalidCredentials")) {

                    log.info("INVALIDCREDENTIALS");
                    request.setAttribute(SET_DIV_ERROR, Alert.INVALIDCREDENTIALS); // Afficher le message d'erreur
                    this.getServletContext().getRequestDispatcher(Page.JSP.LOGIN).forward(request, response); // Rediriger vers la page d'inscription

                }
            } else {

                request.setAttribute(SET_DIV_ERROR, Alert.ERRORSERVER); // Afficher le message d'erreur
                this.getServletContext().getRequestDispatcher(Page.JSP.LOGIN).forward(request, response); // Rediriger vers la page d'inscription

            }

        }


    }
    
    @Override
    public void destroy() {
    
    }
}