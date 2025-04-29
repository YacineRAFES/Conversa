package fr.afpa.dev.pompey.conversa.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils;
import fr.afpa.dev.pompey.conversa.utilitaires.Page;
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
import java.util.List;
import java.util.Map;

import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.*;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.getNameClass;

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
            Cookie[] cookie = request.getCookies();
            String jwt = CookiesUtils.getJWT(cookie);
            String message = request.getParameter("message");
            String iduser = CookiesUtils.getIdUser(cookie);
            String username = CookiesUtils.getUsername(cookie);
            log.info("iduser : " + iduser);
            log.info("username : " + username);
            log.info("message : " + message);
            log.info("jwt : " + jwt);

            if (jwt != null && message != null && iduser != null && username != null) {

                String type = "sendMessages";

                if (type != null && type.equals("sendMessages")) {
                    log.info(getNameClass() + " : type : " + type);
                    //Crée une map
                    Map<String, Object> messageAenvoyer = new HashMap<>();
                    //Ajoute les paramètres à la map
                    messageAenvoyer.put("type", type);
                    messageAenvoyer.put("message", request.getParameter("message"));
                    messageAenvoyer.put("jwt", jwt);
                    messageAenvoyer.put("iduser", iduser);
                    messageAenvoyer.put("username", username);
                    messageAenvoyer.put("idGroupeMessagesPrivee", Integer.valueOf(request.getParameter("idGroupeMessagesPrives")));

                    //Envoyer un message vers API
                    Map<String, Object> apiResponse = envoyerFormulaireVersApi(messageAenvoyer, MESSAGEPRIVE);

                    if (apiResponse != null && apiResponse.get("status").equals("success")) {
                        log.info("apiResponse : OK " + apiResponse);
                        //Récupérer la liste des messages

                    } else if (apiResponse.get("status").equals("error")) {
                        log.info("apiResponse : ERROR " + apiResponse);
                    }

                } else {
                    //Une erreur s'est survenu
                    log.error("Erreur de type MessagesPrives");
                }

            }else{
                log.error("jwt ou message ou iduser ou username est null");
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