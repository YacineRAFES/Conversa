package fr.afpa.dev.pompey.conversa.servlet;
import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils;
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
import org.slf4j.Logger;

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
        jwt = CookiesUtils.getJWT(request.getCookies());

        Map<String, List<Map<String, Object>>> allAmisData = recupererAmisEtDemandes(jwt);
        request.setAttribute("amisRequest", allAmisData.get("amisRequest"));
        request.setAttribute("amisList", allAmisData.get("amisList"));
        request.setAttribute("title", "Amis");

        this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{

            Map<String, Object> apiResponse;
            request.setAttribute("title", "Amis");
            String jwt = null;
            String action = request.getParameter("action");
            String type = request.getParameter("formType");
            String username = request.getParameter("username");

            jwt = CookiesUtils.getJWT(request.getCookies());

            if ("friendSearchForm".equals(type)) {
                log.info("doPost: type : " + type);
                if("search".equals(action)) {
                    log.info("doPost: action : " + action);
                    Map<String, String> formData = new HashMap<>();
                    formData.put("type", type);
                    formData.put("action", action);
                    formData.put("username", username);
                    formData.put("jwt", jwt);
                    Map<String, JsonArray> amisJson = rechercherUnAmis("SearchAndAddFriends", formData, AMIS);
                    log.info("amisJson : " + amisJson);
                    JsonArray amisRechercher = amisJson.get("amisRechercher");
                    log.info("amisRechercher : " + amisRechercher);

                    // Une fois récupérer sous forme de tableau, il faut les afficher dans la page amis.jsp
                    List<Map<String, Object>> amisList = new ArrayList<>();

                    for (JsonValue value : amisRechercher) {
                        JsonObject amiJson = value.asJsonObject();

                        Map<String, Object> ami = new HashMap<>();
                        ami.put("idGroupeMessagesPrives", amiJson.getInt("idGroupeMessagesPrives"));
                        ami.put("statut", amiJson.getString("statut"));
                        ami.put("userId", amiJson.getInt("userId"));
                        ami.put("username", amiJson.getString("username"));

                        amisList.add(ami);
                    }
                    Map<String, List<Map<String, Object>>> allAmisData = recupererAmisEtDemandes(jwt);
                    request.setAttribute("amisRequest", allAmisData.get("amisRequest"));
                    request.setAttribute("amisList", amisList);
                    request.setAttribute("title", "Amis");

                    this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);

                }else if("add".equals(action)) {
                    log.info("doPost: action : " + action);
                    apiResponse = EnvoyerLesDonnees("SearchAndAddFriends", type, action, username, jwt);
                    Reponse(apiResponse, request, response);
                } else {
                    // Gérer d'autres actions si nécessaire
                    this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);
                }
            } else if ("friendRequestResponse".equals(type)) {
                log.info("doPost: type : " + type);
                String id = request.getParameter("id");
                apiResponse = ReponsePourLaDemandeAmis("SearchAndAddFriends", type, action, jwt, id);
                Reponse(apiResponse, request, response);
            }
        }catch (Exception e){
            log.error("Erreur dans le doPost de AmisServlet : " + e.getMessage());
            request.setAttribute(SET_DIV, Alert.ERRORSERVER);
            this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);
        }
    }

    private Map<String, Object> EnvoyerLesDonnees(String method, String type, String action, String username, String jwt) {
        Map<String, String> formData = new HashMap<>();
        formData.put("type", type);
        formData.put("action", action);
        formData.put("username", username);
        formData.put("jwt", jwt);
        return envoyeLaDemandeAmis(method, formData, AMIS);
    }

    private void Reponse(Map<String, Object> apiResponse, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jwt = null;
        jwt = CookiesUtils.getJWT(request.getCookies());
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
                }else if(jsonObject.getString("message").equals("AskFriendRequestSend")) {
                    log.info("Demande d'ami envoyée");
                    request.setAttribute(SET_DIV, Alert.FRIENDREQUESTSENT);
                    this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);
                }else if(jsonObject.getString("message").equals("AcceptFriendRequest")) {
                    log.info("Demande d'ami acceptée");
                    Map<String, List<Map<String, Object>>> allAmisData = recupererAmisEtDemandes(jwt);
                    request.setAttribute("amisRequest", allAmisData.get("amisRequest"));
                    request.setAttribute("amisList", allAmisData.get("amisList"));
                    request.setAttribute("title", "Amis");
                    request.setAttribute(SET_DIV, Alert.ACCEPTFRIENDREQUEST);
                    this.getServletContext().getRequestDispatcher(Page.AMIS).forward(request, response);
                }else if(jsonObject.getString("message").equals("RefuseFriendRequest")) {
                    log.info("Demande d'ami refusée");
                    request.setAttribute(SET_DIV, Alert.REFUSEDFRIENDREQUEST);
                    Map<String, List<Map<String, Object>>> allAmisData = recupererAmisEtDemandes(jwt);
                    request.setAttribute("amisRequest", allAmisData.get("amisRequest"));
                    request.setAttribute("amisList", allAmisData.get("amisList"));
                    request.setAttribute("title", "Amis");
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

    private Map<String, List<Map<String, Object>>> recupererAmisEtDemandes(String jwt) {
        Map<String, String> formData = new HashMap<>();
        formData.put("jwt", jwt);

        Map<String, JsonArray> amisJson = recuperationInfoAmis("GetListFriends", formData, AMIS);
        JsonArray amis = amisJson.get("amis");
        JsonArray demandes = amisJson.get("demandes");

        List<Map<String, Object>> amisList = new ArrayList<>();
        List<Map<String, Object>> amisRequest = new ArrayList<>();

        if (amis != null) {
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

        if (demandes != null) {
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

        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        result.put("amisList", amisList);
        result.put("amisRequest", amisRequest);
        return result;
    }

    private Map<String, Object> ReponsePourLaDemandeAmis(String method, String type, String action, String jwt, String id) {
        Map<String, String> formData = new HashMap<>();
        formData.put("type", type);
        formData.put("action", action);
        formData.put("jwt", jwt);
        formData.put("idFriendRequest", id);
        return envoyeLaDemandeAmis(method, formData, AMIS);
    }
}

