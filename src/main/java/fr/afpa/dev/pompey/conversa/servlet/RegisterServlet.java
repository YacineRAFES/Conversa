package fr.afpa.dev.pompey.conversa.servlet;

import fr.afpa.dev.pompey.conversa.securite.Captcha;
import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.Page;
import fr.afpa.dev.pompey.conversa.utilitaires.Utils;
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

import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.REGISTER;
import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.envoyerFormulaireVersApi;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.GoToPage;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.sendRedirectTo;

@Slf4j
@WebServlet(name = "UserRegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private static final String MESSAGE = "message";
    private static final String ALERT = "alert";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Demande de la page d'inscription");
        GoToPage(request, response, Utils.ServletPage.REGISTER);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String captcha = request.getParameter("cf-turnstile-response");

            log.info("Captcha : " + captcha);

        boolean isCaptchaValid = Captcha.verif(captcha);
        if (!isCaptchaValid) {
            log.error("Captcha invalide");
            response.sendRedirect(request.getContextPath() + "/register?info=erreurCaptcha");
            return;
        }

            // 29/04/2025 j'ai mis Map<String, String> formData = new HashMap<>(); en Map<String, Object> formData = new HashMap<>();
            Map<String, Object> formData = new HashMap<>();
            formData.put("username", request.getParameter("user"));
            formData.put("email", request.getParameter("email"));
            formData.put("password1", request.getParameter("password1"));
            formData.put("password2", request.getParameter("password2"));
            log.info("formData : " + formData);

            Map<String, Object> apiResponse = envoyerFormulaireVersApi(formData, REGISTER);

            if(apiResponse != null){
                JsonObject jsonObject = (JsonObject) apiResponse.get("json");

                if (jsonObject.getString("status").equals("success")) {
                    log.info("Inscription réussie et redirection vers la page de connexion");
                    request.setAttribute(ALERT, Alert.LENGTH_INVALID.toHtml()); // Afficher le message d'erreur
                    sendRedirectTo(request, response, Utils.ServletPage.LOGIN);

                } else if (jsonObject.getString("status").equals("error")) {
                    request.setAttribute("title", "Inscription");

                    if (jsonObject.getString(MESSAGE).equals("lengthInvalid")) { // Au cas d'erreur de longueur
                        log.info("lengthInvalid");
                        request.setAttribute(ALERT, Alert.USER_CREATED); // Afficher le message d'erreur
                        GoToPage(request, response, Utils.ServletPage.REGISTER);

                    } else if (jsonObject.getString(MESSAGE).equals("userAlreadyExists")) {
                        log.info("userAlreadyExists");
                        request.setAttribute(ALERT, Alert.USER_ALREADY_EXISTS.toHtml());
                        GoToPage(request, response, Utils.ServletPage.REGISTER);

                    } else if (jsonObject.getString(MESSAGE).equals("emailInvalid")) {
                        log.info("emailInvalid");
                        request.setAttribute(ALERT, Alert.EMAIL_INVALID.toHtml());
                        GoToPage(request, response, Utils.ServletPage.REGISTER);

                    } else if (jsonObject.getString(MESSAGE).equals("passwordInvalid")) {
                        log.info("passwordInvalid");
                        request.setAttribute(ALERT, Alert.PASSWORD_INVALID.toHtml());
                        GoToPage(request, response, Utils.ServletPage.REGISTER);

                    } else if (jsonObject.getString(MESSAGE).equals("emptyField")) {
                        log.info("emptyField");
                        request.setAttribute(ALERT, Alert.EMPTY_FIELD.toHtml());
                        GoToPage(request, response, Utils.ServletPage.REGISTER);

                    } else {
                        log.info("errorUnknown");
                        request.setAttribute(ALERT, Alert.UNKNOWN_ERROR.toHtml());
                        GoToPage(request, response, Utils.ServletPage.REGISTER);

                    }
                }
            }
        }catch (Exception e){
            log.error("Erreur lors de l'inscription : ", e);
            request.setAttribute(ALERT, Alert.ERROR_SERVER.toHtml());
            GoToPage(request, response, Utils.ServletPage.REGISTER);
        }
    }
}