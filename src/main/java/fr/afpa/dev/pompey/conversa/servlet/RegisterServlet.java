package fr.afpa.dev.pompey.conversa.servlet;

import fr.afpa.dev.pompey.conversa.securite.Captcha;
import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
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

@Slf4j
@WebServlet(name = "UserRegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private static final String MESSAGE = "message";
    private static final String SET_DIV_ERROR = "setDivError";
    private static final String CHEMIN_DE_LA_PAGE = "/JSP/page/register.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Définir le titre de la page
        request.setAttribute("title", "Inscription");

        this.getServletContext().getRequestDispatcher(CHEMIN_DE_LA_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String captcha = request.getParameter("cf-turnstile-response");

        log.info("Captcha : " + captcha);

        boolean isCaptchaValid = Captcha.verif(captcha);
        if (!isCaptchaValid) {
            log.error("Captcha invalide");
            response.sendRedirect(request.getContextPath() + "/register?info=erreurCaptcha");
            return;
        }
        Map<String, String> formData = new HashMap<>();
        formData.put("username", request.getParameter("user"));
        formData.put("email", request.getParameter("email"));
        formData.put("password1", request.getParameter("password1"));
        formData.put("password2", request.getParameter("password2"));

        JsonObject jsonObject = envoyerFormulaireVersApi(formData, REGISTER);
        if (jsonObject.getString("status").equals("success")) {
            log.info("Inscription réussie et redirection vers la page de connexion");
            this.getServletContext().getRequestDispatcher(CHEMIN_DE_LA_PAGE).forward(request, response);
        } else if (jsonObject.getString("status").equals("error")) {

            if (jsonObject.getString(MESSAGE).equals("lengthInvalid")) {
                log.info("lengthInvalid");
                request.setAttribute(SET_DIV_ERROR, Alert.LENGTHINVALID);
                this.getServletContext().getRequestDispatcher(CHEMIN_DE_LA_PAGE).forward(request, response);
            } else if (jsonObject.getString(MESSAGE).equals("userAlreadyExists")) {
                log.info("userAlreadyExists");
                request.setAttribute(SET_DIV_ERROR, Alert.USERALREADYEXISTS);
                this.getServletContext().getRequestDispatcher(CHEMIN_DE_LA_PAGE).forward(request, response);

            } else if (jsonObject.getString(MESSAGE).equals("emailInvalid")) {
                log.info("emailInvalid");
                request.setAttribute(SET_DIV_ERROR, Alert.EMAILINVALID);
                this.getServletContext().getRequestDispatcher(CHEMIN_DE_LA_PAGE).forward(request, response);
            } else if (jsonObject.getString(MESSAGE).equals("passwordInvalid")) {
                log.info("passwordInvalid");
                request.setAttribute(SET_DIV_ERROR, Alert.PASSWORDINVALID);
                this.getServletContext().getRequestDispatcher(CHEMIN_DE_LA_PAGE).forward(request, response);
            } else if (jsonObject.getString(MESSAGE).equals("emptyField")) {
                log.info("emptyField");
                request.setAttribute(SET_DIV_ERROR, Alert.EMPTYFIELD);
                this.getServletContext().getRequestDispatcher(CHEMIN_DE_LA_PAGE).forward(request, response);
            } else {
                log.info("errorUnknown");
                request.setAttribute(SET_DIV_ERROR, Alert.UNKNOWNERROR);
                this.getServletContext().getRequestDispatcher(CHEMIN_DE_LA_PAGE).forward(request, response);
            }
        }
    }
}