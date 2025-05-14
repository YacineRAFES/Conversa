package fr.afpa.dev.pompey.conversa.servlet;

import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils;
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

import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.envoyerFormulaireVersApi;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.*;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.ServletPage.ACCMANAGEMENT;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.ServletPage.ADMIN;

@Slf4j
@WebServlet(name = "AccManagementServlet", value = "/accmanagement")
public class AccManagementServlet extends HttpServlet {
    private static final String SET_DIV = "setDiv";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("accmanagement");
// Récupérer le JWT
        // TODO: A FAIRE !!!!!!!!!!!!!!!!!!!!!
        Map<String, Object> checkJWT = new HashMap<>();
        checkJWT.put("jwt", CookiesUtils.getJWT(request));
        checkJWT.put("action", "getAllUser");
        Map<String, Object> apiResponse = envoyerFormulaireVersApi(checkJWT, SendJSON.ACCMANAGEMENT);
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

                JsonArray utilisateursArray = objects.getJsonArray("users");

                List<Map<String, Object>> utilisateurList = new ArrayList<>();

                for (JsonValue value : utilisateursArray) {
                    JsonObject utilisateurJson = value.asJsonObject();

                    Map<String, Object> utilisateur = new HashMap<>();
                    utilisateur.put("userId", utilisateurJson.getInt("userId"));
                    utilisateur.put("userName", utilisateurJson.getString("userName"));

                    utilisateurList.add(utilisateur);
                }
                request.setAttribute("utilisateursList", utilisateurList);
                GoToPage(request, response, ACCMANAGEMENT, roles);

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
        //Récupération des données saisies par l'Admin
        String action = request.getParameter("action");
        String userId = request.getParameter("userId");
        String userEmail = request.getParameter("userEmail");
        String userName = request.getParameter("userName");
        String userRole = request.getParameter("userRole");
        String valideString = request.getParameter("userValid");

        String newAction;
        Map<String, Object> apiResponse = null;

        if(action.equals("supprimer")){
            //Suppression du compte
            newAction = "deleteAccount";
            apiResponse = getStringToMap(request, newAction, userId);
        }else if(action.equals("get")){
            //Récupération des informations de l'utilisateur
            newAction = "getUser";
            apiResponse = getStringToMap(request, newAction, userId);
        }else if(action.equals("modif")){
            //Modification de l'utilisateur
            newAction = "modifUser";
            if(userId != null && userEmail != null && userName != null && userRole != null && valideString != null) {
                boolean validBoolean = false;
                if(valideString.equals("true")) {
                    validBoolean = true;
                }
                Map<String, Object> actionUser = new HashMap<>();
                actionUser.put("jwt", CookiesUtils.getJWT(request));
                actionUser.put("action", newAction);
                actionUser.put("userId", userId);
                actionUser.put("userEmail", userEmail);
                actionUser.put("userName", userName);
                actionUser.put("userRole", userRole);
                actionUser.put("userIsValid", validBoolean);
                apiResponse = envoyerFormulaireVersApi(actionUser, SendJSON.ACCMANAGEMENT);
            }else{
                request.setAttribute(SET_DIV, Alert.EMPTYFIELD);
                GoToPage(request, response, ACCMANAGEMENT, "admin");
                return;
            }
        } else if (action.equals("resetPassword")) {
            newAction = "sendAskResetPassword";
            apiResponse = getStringToMap(request, newAction, userId);
        }

        if(apiResponse != null) {
            JsonObject jsonObject = (JsonObject) apiResponse.get("json");
            String status = jsonObject.getString("status", "");
            if(status.equals("success")){
                String message = jsonObject.getString("message", "");
                if(message.equals("getUser")) {
                    JsonObject usr = jsonObject.getJsonObject("usr");

                    JsonArray getAllUser = usr.getJsonArray("getAllUser");
                    List<Map<String, Object>> utilisateursList = new ArrayList<>();
                    for (JsonValue value : getAllUser) {
                        JsonObject utilisateurJson = value.asJsonObject();

                        Map<String, Object> utilisateur = new HashMap<>();
                        utilisateur.put("userId", utilisateurJson.getInt("userId"));
                        utilisateur.put("userName", utilisateurJson.getString("userName"));

                        utilisateursList.add(utilisateur);
                    }
                    Map<String, Object> user = jsonObjectToMap(usr.getJsonObject("getUser"));

                    request.setAttribute("utilisateursList", utilisateursList);
                    request.setAttribute("utilisateur", user);
                    GoToPage(request, response, ACCMANAGEMENT, "admin");
                }else if(message.equals("userModified")) {
                    JsonObject usr = jsonObject.getJsonObject("usr");

                    JsonArray getAllUser = usr.getJsonArray("getAllUser");
                    List<Map<String, Object>> utilisateursList = new ArrayList<>();
                    for (JsonValue value : getAllUser) {
                        JsonObject utilisateurJson = value.asJsonObject();

                        Map<String, Object> utilisateur = new HashMap<>();
                        utilisateur.put("userId", utilisateurJson.getInt("userId"));
                        utilisateur.put("userName", utilisateurJson.getString("userName"));

                        utilisateursList.add(utilisateur);
                    }
                    Map<String, Object> user = jsonObjectToMap(usr.getJsonObject("getUser"));

                    request.setAttribute("utilisateursList", utilisateursList);
                    request.setAttribute("utilisateur", user);
                    request.setAttribute(SET_DIV, Alert.USERMODIFIED);
                    GoToPage(request, response, ACCMANAGEMENT, "admin");
                }else if(message.equals("userDeleted")){
                    JsonObject usr = jsonObject.getJsonObject("usr");

                    JsonArray getAllUser = usr.getJsonArray("getAllUser");
                    List<Map<String, Object>> utilisateursList = new ArrayList<>();
                    for (JsonValue value : getAllUser) {
                        JsonObject utilisateurJson = value.asJsonObject();

                        Map<String, Object> utilisateur = new HashMap<>();
                        utilisateur.put("userId", utilisateurJson.getInt("userId"));
                        utilisateur.put("userName", utilisateurJson.getString("userName"));

                        utilisateursList.add(utilisateur);
                    };

                    request.setAttribute("utilisateursList", utilisateursList);
                    request.setAttribute(SET_DIV, Alert.USERDELETED);
                    GoToPage(request, response, ACCMANAGEMENT, "admin");
                }
            }else if(status.equals("error")){
                request.setAttribute(SET_DIV, Alert.ERRORSERVER);
                GoToPage(request, response, ADMIN, "admin");
            }
        }
    }

    @Override
    public void destroy() {

    }

    private Map<String, Object> getStringToMap(HttpServletRequest request, String newAction, String idUser) {
        Map<String, Object> apiResponse;
        Map<String, Object> actionUser = new HashMap<>();
        actionUser.put("jwt", CookiesUtils.getJWT(request));
        actionUser.put("action", newAction);
        actionUser.put("userId", idUser);
        apiResponse = envoyerFormulaireVersApi(actionUser, SendJSON.ACCMANAGEMENT);
        return apiResponse;
    }
}