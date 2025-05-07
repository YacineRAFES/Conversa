package fr.afpa.dev.pompey.conversa.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;
import java.util.Map;

import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.*;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.*;

@Slf4j
@WebServlet(name = "MessagePriveServlet", value = "/messageprive")
public class MessagePriveServlet extends HttpServlet {
    private static final String SET_DIV = "setDiv";

    @Override
    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer le JWT
        Map<String, Object> checkJWT = new HashMap<>();
        checkJWT.put("jwt", CookiesUtils.getJWT(request));
        Map<String, Object> apiResponse = envoyerFormulaireVersApi(checkJWT, CHECKJWT);
        JsonObject jsonObject = (JsonObject) apiResponse.get("json");
        log.info("apiResponse : " + apiResponse);
        log.info("jsonObject : " + jsonObject);

        if (jsonObject != null && jsonObject.containsKey("status")) {
            String status = jsonObject.getString("status", "");
            JsonObject user = jsonObject.getJsonObject("user");
            String roles = user.getString("userRole");
            if (status.equals("success")) {
                if(roles != null){
                    definirPage(request, ServletPage.MESSAGEPRIVE, roles);
                    this.getServletContext().getRequestDispatcher(Page.JSP.MESSAGES_PRIVEE).forward(request, response);
                }else{
                    backToPageLogin(request, response);
                }
            } else if (status.equals("error")) {
                log.info("Status : " + status);
                String message = jsonObject.getString("message", "");

                if ("jwtInvalide".equals(message)) {
                    log.info(message);
                    request.setAttribute(SET_DIV, Alert.AUTHENTICATIONEXPIRED);
                    backToPageLogin(request, response);
                }else if("ErrorServer".equals(message)) {
                    log.info(message);
                    backToPageLogin(request, response);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Cookie[] cookie = request.getCookies();
            String jwt = CookiesUtils.getJWT(request);
            String iduser = CookiesUtils.getIdUser(cookie);
            String username = CookiesUtils.getUsername(cookie);
            log.info(getNameClass() + "iduser : " + iduser);
            log.info(getNameClass() + "username : " + username);
            log.info(getNameClass() + "jwt : " + jwt);

            if (jwt != null && iduser != null && username != null) {
                log.info(getNameClass() + " jwt != null && iduser != null && username != null ");
                //Récupérer le type de la requête
                String type = request.getParameter("type");

                if (type != null && type.equals("sendMessages")) {
                    log.info(getNameClass() + " if (type != null && type.equals(\"sendMessages\")) {");
                    String message = request.getParameter("message");
                    if(message == null) {
                        log.error("message est null");
                        return;
                    }
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

                    ApiResponse(apiResponse, request, response);
                } else if(type.equals("signaler")){
                    log.info(getNameClass() + " : type : " + type);
                    //Crée une map
                    Map<String, Object> messageASignaler = new HashMap<>();
                    //Ajoute les paramètres à la map
                    messageASignaler.put("type", type);
                    messageASignaler.put("idMessage", Integer.valueOf(request.getParameter("idMessage")));
                    messageASignaler.put("jwt", jwt);
                    messageASignaler.put("iduser", iduser);
                    messageASignaler.put("username", username);
                    messageASignaler.put("idGroupeMessagesPrivee", Integer.valueOf(request.getParameter("idGroupeMessagesPrives")));

                    boolean success = ApiResponse(messageASignaler, request, response);
                    if (success) {
                        return;
                    }

                } else if(type.equals("supprimer")){
                    log.info(getNameClass() + " : type : " + type);
                    //Crée une map
                    Map<String, Object> messageASignaler = new HashMap<>();
                    //Ajoute les paramètres à la map
                    messageASignaler.put("type", type);
                    messageASignaler.put("idMessage", Integer.valueOf(request.getParameter("idMessage")));
                    messageASignaler.put("jwt", jwt);
                    messageASignaler.put("iduser", iduser);
                    messageASignaler.put("username", username);
                    messageASignaler.put("idGroupeMessagesPrivee", Integer.valueOf(request.getParameter("idGroupeMessagesPrives")));

                    boolean success = ApiResponse(messageASignaler, request, response);
                    if (success) {
                        return;
                    }

                } else {
                    //Une erreur s'est survenu
                    log.error("Erreur de type MessagesPrives");
                }

            }else{
                log.error("jwt ou message ou iduser ou username est null");
            }

        } catch (Exception e) {
            log.error("Une erreur s'est produite : " + e.getMessage());
            request.setAttribute(SET_DIV, Alert.ERRORSERVER);
            this.getServletContext().getRequestDispatcher(Page.JSP.MESSAGES_PRIVEE).forward(request, response);
        }

    }

//    private void EnvoyeLesInfosVersWeb(HttpServletResponse response, <String, Object> getAllMessages) throws ServletException, IOException {
//
//
//    }

    private boolean ApiResponse(Map<String, Object> messageASignaler, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> apiResponse = envoyerFormulaireVersApi(messageASignaler, MESSAGEPRIVE);
        if(apiResponse == null) {
            log.error("apiResponse est null");
            return false;
        }
        JsonObject jsonObject = (JsonObject) apiResponse.get("json");
        String status = jsonObject.get("status").toString();
        String message = jsonObject.get("message").toString();
        if (status != null && message != null && status.equals("success")) {
            log.info("jsonObject : OK " + jsonObject);
            return true;
        } else if (status.equals("error")) {
            log.info("jsonObject : ERROR " + jsonObject);
            if (message.equals("jwtInvalide")) {
                log.info(message);
                request.setAttribute(SET_DIV, Alert.AUTHENTICATIONEXPIRED);
                backToPageLogin(request, response);
            } else if (message.equals("ErrorServer")) {
                log.info(message);
                request.setAttribute(SET_DIV, Alert.ERRORSERVER);
                backToPageLogin(request, response);
            }
        }
        return false;
    }

}