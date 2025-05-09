package fr.afpa.dev.pompey.conversa.servlet;

import fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.AMIS;
import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.getAllAmis;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.backToPageLogin;

@Slf4j
@WebServlet(name = "AmisJsonServlet", value = "/amisjson")
public class AmisJsonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> recupToutLesAmis = new HashMap<>();
        recupToutLesAmis.put("method", "getAllAmis");
        recupToutLesAmis.put("jwt", CookiesUtils.getJWT(request));
        Map<String, Object> apiResponse = getAllAmis(recupToutLesAmis, AMIS);

        //Si le statut est success, on renvoie les messages
        if (apiResponse != null && apiResponse.get("status").equals("success")) {
            log.info("apiResponse : OK " + apiResponse);
            List<Map<String, Object>> objects = (List<Map<String, Object>>) apiResponse.get("getAllAmis");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK); // Code 200 pour succès
            response.getWriter().write(objects.toString());
            log.info("getAllAmis : " + objects);

        }else if (apiResponse != null && apiResponse.get("status").equals("error")) {
            if(apiResponse.get("message").equals("jwtInvalide")) {
                log.error("apiResponse : jwtInvalide " + apiResponse);
                // On lui retourne vers la page de login
                backToPageLogin(request, response);
            }else if(apiResponse.get("message").equals("userNotFound")){
                log.error("apiResponse : userNotFound " + apiResponse);
                // On lui retourne vers la page de login
                backToPageLogin(request, response);
            }
            //créer du json
            JSONObject json = new JSONObject();
            json.put("status", "ErrorServer");

            log.error("apiResponse : ERROR " + apiResponse);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Code 400 pour erreur
            response.getWriter().write(json.toString());
        } else {
            log.error("apiResponse : ERROR " + apiResponse);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Code 400 pour erreur
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Erreur lors de la récupération des messages\"}");
        }

    }




}
