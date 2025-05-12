package fr.afpa.dev.pompey.conversa.servlet;

import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils;
import fr.afpa.dev.pompey.conversa.utilitaires.Page;
import fr.afpa.dev.pompey.conversa.utilitaires.SendJSON;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.CHECKJWT;
import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.envoyerFormulaireVersApi;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.*;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.ServletPage.ADMIN;

@Slf4j
@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {
    private static final String SET_DIV = "setDiv";

    @Override
    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// Récupérer le JWT
        // TODO: A FAIRE !!!!!!!!!!!!!!!!!!!!!
        Map<String, Object> checkJWT = new HashMap<>();
        checkJWT.put("jwt", CookiesUtils.getJWT(request));
        checkJWT.put("action", "getAllSignalements");
        Map<String, Object> apiResponse = envoyerFormulaireVersApi(checkJWT, SendJSON.ADMIN);
        JsonObject jsonObject = (JsonObject) apiResponse.get("json");
        log.info("apiResponse : " + apiResponse);
        log.info("jsonObject : " + jsonObject);

        if (jsonObject != null && jsonObject.containsKey("status")) {
            String status = jsonObject.getString("status", "");
            JsonObject objects = jsonObject.getJsonObject("objects");
            JsonObject user = objects.getJsonObject("user");
            String roles = user.getString("userRole");
            if (!"admin".equalsIgnoreCase(roles)) {
                log.info("Rôle de l'utilisateur : " + roles);
                backToPageLogin(request, response);
                return;
            }
            if (status.equals("success")) {
                log.info("Status : " + status);

                //Je récupère tout les signalements

                JsonArray signalementsArray = objects.getJsonArray("signalements");

                List<Map<String, Object>> signalementList = new ArrayList<>();

                for (JsonValue value : signalementsArray) {
                    JsonObject signalementJson = value.asJsonObject();

                    Map<String, Object> signalement = new HashMap<>();
                    signalement.put("messageId", signalementJson.getInt("messageId"));

                    signalementList.add(signalement);
                }
                request.setAttribute("signalementList", signalementList);
                GoToPage(request, response, ADMIN, roles);

            }else if(status.equals("error")) {
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
        String action = request.getParameter("action");
        String idUser = request.getParameter("idUser");
        String raison = request.getParameter("raison");
        String idMessage = request.getParameter("IdMessage");

        String newAction = null;
        Map<String, Object> apiResponse = null;
        if(action.equals("supprimer")){
            newAction = "deleteSignalement";
            Map<String, Object> actionSignalement = new HashMap<>();
            actionSignalement.put("jwt", CookiesUtils.getJWT(request));
            actionSignalement.put("action", newAction);
            actionSignalement.put("IdMessage", idMessage);
            apiResponse = envoyerFormulaireVersApi(actionSignalement, SendJSON.ADMIN);
        }else if(action.equals("ban")){
            newAction = "banSignalement";

            Map<String, Object> actionSignalement = new HashMap<>();
            actionSignalement.put("jwt", CookiesUtils.getJWT(request));
            actionSignalement.put("action", newAction);
            actionSignalement.put("IdMessage", idMessage);
            actionSignalement.put("idUser", idUser);
            actionSignalement.put("raison", raison);
            apiResponse = envoyerFormulaireVersApi(actionSignalement, SendJSON.ADMIN);
        }else if(action.equals("avertissement")){
            newAction = "warningSignalement";

            Map<String, Object> actionSignalement = new HashMap<>();
            actionSignalement.put("jwt", CookiesUtils.getJWT(request));
            actionSignalement.put("action", newAction);
            actionSignalement.put("IdMessage", idMessage);
            actionSignalement.put("idUser", idUser);
            actionSignalement.put("raison", raison);
            apiResponse = envoyerFormulaireVersApi(actionSignalement, SendJSON.ADMIN);
        }else if(action.equals("get")){
            newAction = "getSignalement";

            Map<String, Object> actionSignalement = new HashMap<>();
            actionSignalement.put("jwt", CookiesUtils.getJWT(request));
            actionSignalement.put("action", newAction);
            actionSignalement.put("IdMessage", idMessage);
            apiResponse = envoyerFormulaireVersApi(actionSignalement, SendJSON.ADMIN);
        }

        Map<String, Object> actionSignalement = new HashMap<>();
        actionSignalement.put("jwt", CookiesUtils.getJWT(request));
        actionSignalement.put("action", newAction);
        actionSignalement.put("IdMessage", idMessage);
        if(apiResponse != null) {
            JsonObject jsonObject = (JsonObject) apiResponse.get("json");
            String status = jsonObject.getString("status", "");
            if(status.equals("success")){
                String message = jsonObject.getString("message", "");
                if(message.equals("getSignalement")) {
                    JsonObject sgl = jsonObject.getJsonObject("sgl");

                    JsonArray getAllSignalement = sgl.getJsonArray("getAllSignalement");
                    List<Map<String, Object>> signalementList = new ArrayList<>();
                    for (JsonValue value : getAllSignalement) {
                        JsonObject signalementJson = value.asJsonObject();

                        Map<String, Object> signalement = new HashMap<>();
                        signalement.put("messageId", signalementJson.getInt("messageId"));

                        signalementList.add(signalement);
                    }
                    JsonObject signalement = sgl.getJsonObject("signalements");

                    request.setAttribute("signalementList", signalementList);
                    request.setAttribute("signalement", signalement);
                    GoToPage(request, response, ADMIN, "admin");
                }else if(message.equals("banSignalement")) {
                    JsonObject sgl = jsonObject.getJsonObject("sgl");

                    JsonArray getAllSignalement = sgl.getJsonArray("getAllSignalement");
                    List<Map<String, Object>> signalementList = new ArrayList<>();
                    for (JsonValue value : getAllSignalement) {
                        JsonObject signalementJson = value.asJsonObject();

                        Map<String, Object> signalement = new HashMap<>();
                        signalement.put("messageId", signalementJson.getInt("messageId"));

                        signalementList.add(signalement);
                    }

                    request.setAttribute("signalementList", signalementList);
                    request.setAttribute(SET_DIV, Alert.USERBAN);
                    GoToPage(request, response, ADMIN, "admin");
                }else if(message.equals("deleteSignalement")){
                    JsonObject sgl = jsonObject.getJsonObject("sgl");

                    JsonArray getAllSignalement = sgl.getJsonArray("getAllSignalement");
                    List<Map<String, Object>> signalementList = new ArrayList<>();
                    for (JsonValue value : getAllSignalement) {
                        JsonObject signalementJson = value.asJsonObject();

                        Map<String, Object> signalement = new HashMap<>();
                        signalement.put("messageId", signalementJson.getInt("messageId"));

                        signalementList.add(signalement);
                    }

                    request.setAttribute("signalementList", signalementList);
                    request.setAttribute(SET_DIV, Alert.SIGNALEMENTISDELETE);
                    GoToPage(request, response, ADMIN, "admin");
                }else if(message.equals("warningSignalement")) {
                    JsonObject sgl = jsonObject.getJsonObject("sgl");

                    JsonArray getAllSignalement = sgl.getJsonArray("getAllSignalement");
                    List<Map<String, Object>> signalementList = new ArrayList<>();
                    for (JsonValue value : getAllSignalement) {
                        JsonObject signalementJson = value.asJsonObject();

                        Map<String, Object> signalement = new HashMap<>();
                        signalement.put("messageId", signalementJson.getInt("messageId"));

                        signalementList.add(signalement);
                    }

                    request.setAttribute("signalementList", signalementList);
                    request.setAttribute(SET_DIV, Alert.USERWARNING);
                    GoToPage(request, response, ADMIN, "admin");
                }
            }else if(status.equals("error")){
                request.setAttribute(SET_DIV, Alert.ERRORSERVER);
                GoToPage(request, response, ADMIN, "admin");
            }
        }
    }

}