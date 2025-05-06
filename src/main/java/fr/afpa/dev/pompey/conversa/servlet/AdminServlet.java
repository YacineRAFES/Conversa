package fr.afpa.dev.pompey.conversa.servlet;

import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils;
import fr.afpa.dev.pompey.conversa.utilitaires.Page;
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
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.backToPageLogin;

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
        String jwt = CookiesUtils.getJWT(request);
        Map<String, Object> checkJWT = new HashMap<>();
        checkJWT.put("jwt", jwt);
        Map<String, Object> apiResponse = envoyerFormulaireVersApi(checkJWT, CHECKJWT);
        JsonObject jsonObject = (JsonObject) apiResponse.get("json");
        log.info("apiResponse : " + apiResponse);
        log.info("jsonObject : " + jsonObject);

        if (jsonObject != null && jsonObject.containsKey("status")) {
            String status = jsonObject.getString("status", "");
            String roles = CookiesUtils.getRole(request.getCookies());
            if (status.equals("success")) {

                if(roles.equals("admin")){
                    log.info("Status : "+status);
                    // Définir le titre de la page
                    request.setAttribute("title", "Admin");
                    request.setAttribute("menu", "admin");
                    // Définir le nom du fichier JavaScript à inclure
//                    request.setAttribute("js", "admin.js");
                    this.getServletContext().getRequestDispatcher(Page.JSP.ADMIN).forward(request, response);
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

    }

}