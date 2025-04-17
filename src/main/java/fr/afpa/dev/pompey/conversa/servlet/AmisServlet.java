package fr.afpa.dev.pompey.conversa.servlet;
import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.Page;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.*;

@Slf4j
@WebServlet(name = "AmisServlet", value = "/amis")
public class AmisServlet extends HttpServlet {

    @Override
    public void init() {
    
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // quand l'utilisateur entre dans la page et le servlet doit demander une reponse à l'api pour récupérer tout les amis de l'utilisateur
        String jwt = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }
        Map<String, String> formData = new HashMap<>();
        formData.put("jwt", jwt);
        log.info("formData : " + formData);
        JsonArray amisJson = recupererArray(formData, AMIS, GET);

        // Une fois récupérer sous forme de tableau, il faut les afficher dans la page amis.jsp
        List<Map<String, Object>> amisList = new ArrayList<>();

        if (amisJson == null) {
            log.error("amisJson est null, la récupération a échoué");
            request.setAttribute("amisList", null);
        } else {
            for (JsonValue value : amisJson) {
                JsonObject amiJson = value.asJsonObject();

                Map<String, Object> ami = new HashMap<>();
                ami.put("idGroupeMessagesPrives", amiJson.getInt("idGroupeMessagesPrives"));
                ami.put("statut", amiJson.getString("statut"));
                ami.put("dateDemande", amiJson.getString("dateDemande"));
                ami.put("userIdAmiDe", amiJson.getInt("userIdAmiDe"));
                ami.put("userIdDemandeur", amiJson.getInt("userIdDemandeur"));
                ami.put("userId", amiJson.getInt("userId"));
                ami.put("username", amiJson.getString("username"));

                amisList.add(ami);
            }
        }
        // request qui contient tout les informations envoyées par le client lors de la requête HTTP vers le serveur
        // response qui permet d'envoyer une réponse HTTP au client
        // Définir le titre de la page
        request.setAttribute("title", "Amis");

        this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "Amis");
        String jwt = null;
        request.setAttribute("title", "Amis");
        String action = request.getParameter("action");
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        //Verification JWT

        if("search".equals(action)) {
            Map<String, String> formData = new HashMap<>();
            formData.put("search", request.getParameter("searchInput"));
            formData.put("action", action);
            formData.put("jwt", jwt);
            JsonArray amisJson = recupererArray(formData, AMIS, POST);

            // Une fois récupérer sous forme de tableau, il faut les afficher dans la page amis.jsp
            List<Map<String, Object>> amisList = new ArrayList<>();

            for (JsonValue value : amisJson) {
                JsonObject amiJson = value.asJsonObject();

                Map<String, Object> ami = new HashMap<>();
                ami.put("idGroupeMessagesPrives", amiJson.getInt("idGroupeMessagesPrives"));
                ami.put("statut", amiJson.getString("statut"));
                ami.put("userId", amiJson.getInt("userId"));
                ami.put("username", amiJson.getString("username"));

                amisList.add(ami);
            }

        }else if("add".equals(action)) {
            Map<String, String> formData = new HashMap<>();
            formData.put("search", request.getParameter("searchInput"));
            formData.put("action", action);
            formData.put("jwt", jwt);
            Map<String, Object> apiResponse = envoyerFormulaireVersApi(formData, AMIS);
            if(apiResponse != null) {
                JsonObject jsonObject = (JsonObject) apiResponse.get("json");
            }

        } else {
            // Gérer d'autres actions si nécessaire
            this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);
        }
    }
    
    @Override
    public void destroy() {
    
    }
}