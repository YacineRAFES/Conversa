package fr.afpa.dev.pompey.conversa.servlet;
import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.Page;
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
    private static final String SET_DIV = "setDiv";

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
        Map<String, JsonArray>  amisJson = envoyerFormulaireVersApiAvecObjects(formData, AMIS);
        JsonArray amis = amisJson.get("amis");
        JsonArray demandes = amisJson.get("demandes");

        // Une fois récupérer sous forme de tableau, il faut les afficher dans la page amis.jsp
        List<Map<String, Object>> amisList = new ArrayList<>();
        List<Map<String, Object>> amisRequest = new ArrayList<>();

        if (amis == null) {
            log.error("amisJson est null, la récupération a échoué");
            request.setAttribute("amisList", null);
        } else {
            for (JsonValue value : amis) {
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

        if (demandes == null) {
            log.error("amisJson est null, la récupération a échoué");
            request.setAttribute("amisRequest", null);
        } else {
            for (JsonValue value : demandes) {
                JsonObject demandesJson = value.asJsonObject();

                Map<String, Object> demande = new HashMap<>();
                demande.put("idGroupeMessagesPrives", demandesJson.getInt("idGroupeMessagesPrives"));
                demande.put("statut", demandesJson.getString("statut"));
                demande.put("dateDemande", demandesJson.getString("dateDemande"));
                demande.put("userIdAmiDe", demandesJson.getInt("userIdAmiDe"));
                demande.put("userIdDemandeur", demandesJson.getInt("userIdDemandeur"));
                demande.put("userId", demandesJson.getInt("userId"));
                demande.put("username", demandesJson.getString("username"));

                amisRequest.add(demande);
            }
        }
        // request qui contient tout les informations envoyées par le client lors de la requête HTTP vers le serveur
        // response qui permet d'envoyer une réponse HTTP au client
        // Définir le titre de la page
        request.setAttribute("title", "Amis");
        request.setAttribute("amisList", amisList);
        request.setAttribute("amisRequest", amisRequest);

        this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> apiResponse = null;
        request.setAttribute("title", "Amis");
        String jwt = null;
        String action = request.getParameter("action");
        String type = request.getParameter("formType");
        String username = request.getParameter("username");

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        if ("friendSearchForm".equals(type)) {
            if("search".equals(action)) {
                Map<String, String> formData = new HashMap<>();
                formData.put("type", type);
                formData.put("action", action);
                formData.put("username", username);
                formData.put("jwt", jwt);
                JsonArray amisJson = recupererArray(formData, AMIS);

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
                apiResponse = EnvoyerLesDonnees(type, action, username, jwt);

            } else {
                // Gérer d'autres actions si nécessaire
                this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);
            }
        } else if ("friendRequestResponse".equals(type)) {
            String id = request.getParameter("id");
            apiResponse = ReponseLaDemandeAmis(type, action, id, jwt);
        }
        if(apiResponse != null) {
            JsonObject jsonObject = (JsonObject) apiResponse.get("json");

            if (jsonObject.getString("status").equals("success")) {

                if(jsonObject.getString("message").equals("friendRequestSent")) {
                    log.info("Demande d'ami envoyée");
                    request.setAttribute(SET_DIV, Alert.FRIENDREQUESTSENT);
                    this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);

                } else if(jsonObject.getString("message").equals("friendRequestAlreadySent")) {
                    log.error("Demande d'ami déjà envoyée");
                    request.setAttribute(SET_DIV, Alert.FRIENDREQUESTALREADYSENT);
                    this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);

                } else if(jsonObject.getString("message").equals("friendRequestAlreadyAccepted")) {
                    log.error("Demande d'ami déjà acceptée");
                    request.setAttribute(SET_DIV, Alert.FRIENDREQUESTALREADYACCEPTED);
                    this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);

                } else {
                    log.error("Erreur lors de l'ajout d'ami");
                    request.setAttribute(SET_DIV, Alert.ERRORSERVER);
                    this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);
                }

            } else if (jsonObject.getString("status").equals("error")) {
                log.error("Erreur lors de l'ajout d'ami");
                request.setAttribute(SET_DIV, Alert.ERRORSERVER);
                this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);
            }
        }else{
            log.error("apiResponse est null, la récupération a échoué");
            request.setAttribute(SET_DIV, Alert.ERRORSERVER);
            this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);
        }

    }

    private Map<String, Object> EnvoyerLesDonnees(String type, String action, String username, String jwt) {
        Map<String, String> formData = new HashMap<>();
        formData.put("type", type);
        formData.put("action", action);
        formData.put("username", username);
        formData.put("jwt", jwt);
        return envoyerFormulaireVersApi(formData, AMIS);
    }

    private Map<String, Object> ReponseLaDemandeAmis(String type, String action, String id, String jwt) {
        Map<String, String> formData = new HashMap<>();
        formData.put("type", type);
        formData.put("action", action);
        formData.put("idFriendRequest", id);
        formData.put("jwt", jwt);
        return envoyerFormulaireVersApi(formData, AMIS);
    }
}

