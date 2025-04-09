package fr.afpa.dev.pompey.conversa.servlet;

import fr.afpa.dev.pompey.conversa.securite.CSRFTokenFilter;
import fr.afpa.dev.pompey.conversa.securite.Captcha;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(name = "UserRegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Définir le titre de la page
        request.setAttribute("title", "Inscription");

        // Définir le nom du fichier JavaScript à inclure
        request.setAttribute("js", "register.js");

        this.getServletContext().getRequestDispatcher("/JSP/page/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String captcha = request.getParameter("cf-turnstile-response");

        log.info("Captcha : " + captcha);

        boolean isCaptchaValid = Captcha.verif(captcha);
        if (!isCaptchaValid) {
            log.error("Captcha invalide");
            response.sendRedirect(request.getContextPath() + "/register?error=captcha");
            return;
        }
    }

    @Override
    public void destroy() {

    }
}