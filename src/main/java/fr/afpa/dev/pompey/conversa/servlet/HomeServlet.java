package fr.afpa.dev.pompey.conversa.servlet;
import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils;
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

import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.CHECKJWT;
import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.envoyerFormulaireVersApi;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.*;

@Slf4j
@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {
    private static final String SET_DIV = "setDiv";

    @Override
    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Home servlet");
        // Récupérer le JWT
        Map<String, Object> checkJWT = new HashMap<>();
        checkJWT.put("jwt", CookiesUtils.getJWT(request));
        Map<String, Object> apiResponse = envoyerFormulaireVersApi(checkJWT, CHECKJWT);
        JsonObject jsonObject = (JsonObject) apiResponse.get("json");
        log.info("apiResponse : " + apiResponse);
        log.info("jsonObject : " + jsonObject);

        if (jsonObject != null && jsonObject.containsKey("status")) {
            log.info("Status : " + jsonObject.getString("status"));
            String status = jsonObject.getString("status", "");
            JsonObject user = jsonObject.getJsonObject("user");
            if(user == null){
                log.info("user is null");
                backToPageLogin(request, response);
                return;
            }
            String roles = user.getString("userRole");
            if (status.equals("success")) {
                log.info("Status : " + status);
                if(roles != null){
                    log.info("roles : " + roles);
                    GoToPage(request, response, Utils.ServletPage.HOME, roles);
                }else{

                    log.info("Pas de roles, donc backtoPageLogin");
                    backToPageLogin(request, response);
                }
            } else if (status.equals("error")) {
                log.info("Status : " + status);
                String message = jsonObject.getString("message", "");

                if ("jwtInvalide".equals(message)) {

                    log.info("jwtInvalide, donc backtoPageLogin");
                    log.info(message);
                    request.setAttribute(SET_DIV, Alert.AUTHENTICATION_EXPIRED.toHtml());
                    backToPageLogin(request, response);
                }else if("ErrorServer".equals(message)) {

                    log.info("ErrorServer, donc backtoPageLogin");
                    log.info(message);
                    backToPageLogin(request, response);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    
    @Override
    public void destroy() {
    
    }
}