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

    }

}