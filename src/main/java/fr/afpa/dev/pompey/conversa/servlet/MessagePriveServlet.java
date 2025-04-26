package fr.afpa.dev.pompey.conversa.servlet;

import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils;
import fr.afpa.dev.pompey.conversa.utilitaires.Page;
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
@WebServlet(name = "MessagePriveServlet", value = "/messageprive")
public class MessagePriveServlet extends HttpServlet {
    private static final String SET_DIV = "setDiv";

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
        try {
            //Recupere le JWT
            String jwt = null;
            jwt = CookiesUtils.getJWT(request.getCookies());
            log.info("msg recu : " + request.getParameter("message"));
            log.info("JWT: {}", jwt);

            //Crée une map
            Map<String, String> messageAenvoyer = new HashMap<>();
            messageAenvoyer.put("message", request.getParameter("message"));
            messageAenvoyer.put("jwt", CookiesUtils.getJWT(request.getCookies()));

            //Envoyer un message vers API
            Map<String, Object> apiResponse = envoyerFormulaireVersApi(messageAenvoyer, MESSAGEPRIVE);
            if(apiResponse != null) {
                if(apiResponse.get("status").equals("success")) {
                    log.info("apiResponse : OK " + apiResponse );
                }else if(apiResponse.get("status").equals("error")) {
                    log.info("apiResponse : ERROR " + apiResponse );
                }
            }



        } catch (Exception e) {
            log.error("apiResponse est null, la récupération a échoué");
            request.setAttribute(SET_DIV, Alert.ERRORSERVER);
            this.getServletContext().getRequestDispatcher(Page.JSP.AMIS).forward(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}