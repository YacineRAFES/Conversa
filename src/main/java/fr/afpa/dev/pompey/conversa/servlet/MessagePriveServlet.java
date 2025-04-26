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
import java.util.List;
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
            log.info("msg recu : " + request.getParameter("message"));

            String type = request.getParameter("type");
            if(type != null) {
                if(type.equals("getAllMessages")){
                    //Crée une map
                    Map<String, String> recupToutLesMessages = new HashMap<>();
                    recupToutLesMessages.put("type", type);
                    recupToutLesMessages.put("jwt", CookiesUtils.getJWT(request.getCookies()));
                    Map<String, Object> apiResponse = getAllMessages(recupToutLesMessages, MESSAGEPRIVE);
                    if(apiResponse != null) {
                        if(apiResponse.get("status").equals("success")) {
                            log.info("apiResponse : OK " + apiResponse );
                            List<Map<String, Object>> getAllMessages = (List<Map<String, Object>>) apiResponse.get("getAllMessages");

                            //Envoyer les infos vers le web en json
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.setStatus(HttpServletResponse.SC_OK); // Code 200 pour succès
                            response.getWriter().write(getAllMessages.toString());

                        }else if(apiResponse.get("status").equals("error")) {
                            log.info("apiResponse : ERROR " + apiResponse );
                        }
                    }
                }else if(type.equals("sendMessage")){
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
                }else{
                    //Une erreur s'est survenu
                    log.error("Erreur de type MessagesPrives");
                }
            }

        } catch (Exception e) {
            log.error("apiResponse est null, la récupération a échoué");
            request.setAttribute(SET_DIV, Alert.ERRORSERVER);
            this.getServletContext().getRequestDispatcher(Page.JSP.AMIS).forward(request, response);
        }

    }

//    private void EnvoyeLesInfosVersWeb(HttpServletResponse response, <String, Object> getAllMessages) throws ServletException, IOException {
//
//
//    }

}