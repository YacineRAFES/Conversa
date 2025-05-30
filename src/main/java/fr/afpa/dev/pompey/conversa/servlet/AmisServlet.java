package fr.afpa.dev.pompey.conversa.servlet;
import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils;
import fr.afpa.dev.pompey.conversa.utilitaires.Page;
import fr.afpa.dev.pompey.conversa.utilitaires.Utils;
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

import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.*;
import static fr.afpa.dev.pompey.conversa.utilitaires.Utils.*;

@Slf4j
@WebServlet(name = "AmisServlet", value = "/amis")
public class AmisServlet extends HttpServlet {
    private static final String SET_DIV = "setDiv";

    @Override
    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer le JWT
        String jwt = CookiesUtils.getJWT(request);
        log.info(getNameClass());
        Map<String, Object> checkJWT = new HashMap<>();
        checkJWT.put("jwt", jwt);
        Map<String, Object> apiResponse = envoyerFormulaireVersApi(checkJWT, CHECKJWT);
        JsonObject jsonObject = (JsonObject) apiResponse.get("json");
        log.info("apiResponse : " + apiResponse);
        log.info("jsonObject : " + jsonObject);

        if (jsonObject != null && jsonObject.containsKey("status")) {
            String status = jsonObject.getString("status", "");
            JsonObject user = jsonObject.getJsonObject("user");
            if(user == null){
                backToPageLogin(request, response);
                return;
            }
            String roles = user.getString("userRole");
            if (status.equals("success")) {
                Map<String, List<Map<String, Object>>> allAmisData = recupererAmisEtDemandes(CookiesUtils.getJWT(request));
                request.setAttribute("amisRequest", allAmisData.get("amisRequest"));
                request.setAttribute("amisList", allAmisData.get("amisList"));
                GoToPage(request, response, Utils.ServletPage.AMIS, roles);
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


        // quand l'utilisateur entre dans la page et le servlet doit demander une reponse à l'api pour récupérer tout les amis de l'utilisateur


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

            jwt = CookiesUtils.getJWT(request);

            if ("friendSearchForm".equals(type)) {
                log.info("doPost: type : " + type);
                if("search".equals(action)) {
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

                    GoToPage(request, response, ServletPage.AMIS);
                }else if("add".equals(action)) {
                    log.info("doPost: action : " + action);
                    apiResponse = EnvoyerLesDonnees("SearchAndAddFriends", jwt, type, action, username);
                    Reponse(apiResponse, request, response);
                } else {
                    // Gérer d'autres actions si nécessaire
                    request.setAttribute(SET_DIV, Alert.ERRORFORM);
                    GoToPage(request, response, ServletPage.AMIS);
                }
            } else if ("friendRequestResponse".equals(type)) {
                log.info("doPost: type : " + type);
                String id = request.getParameter("id");
                apiResponse = ReponsePourLaDemandeAmis("SearchAndAddFriends", jwt, type, action, id);
                Reponse(apiResponse, request, response);
            }
        }catch (Exception e){
            log.error("Erreur dans le doPost de AmisServlet : " + e.getMessage());
            request.setAttribute(SET_DIV, Alert.ERRORSERVER);
            this.getServletContext().getRequestDispatcher(Page.JSP.AMIS).forward(request, response);
        }
    }

    private Map<String, Object> EnvoyerLesDonnees(String method, String jwt, String type, String action, String username) {
        Map<String, String> formData = new HashMap<>();
        formData.put("type", type);
        formData.put("action", action);
        formData.put("username", username);
        return envoyeLaDemandeAmis(method, jwt, formData, AMIS);
    }

    private void Reponse(Map<String, Object> apiResponse, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jwt = null;
        jwt = CookiesUtils.getJWT(request);
        if(apiResponse != null) {
            JsonObject jsonObject = (JsonObject) apiResponse.get("json");

            if (jsonObject.getString("status").equals("success")) {
                if(jsonObject.getString("message").equals("friendRequestSent")) {
                    log.info("Demande d'ami envoyée");
                    request.setAttribute(SET_DIV, Alert.FRIENDREQUESTSENT);
                    GoToPage(request, response, ServletPage.AMIS);

                } else if(jsonObject.getString("message").equals("friendRequestAlreadySent")) {
                    log.error("Demande d'ami déjà envoyée");
                    request.setAttribute(SET_DIV, Alert.FRIENDREQUESTALREADYSENT);
                    GoToPage(request, response, ServletPage.AMIS);

                } else if(jsonObject.getString("message").equals("friendRequestAlreadyAccepted")) {
                    log.error("Demande d'ami déjà acceptée");
                    request.setAttribute(SET_DIV, Alert.FRIENDREQUESTALREADYACCEPTED);
                    GoToPage(request, response, ServletPage.AMIS);

                }else if(jsonObject.getString("message").equals("AskFriendRequestSend")) {
                    log.info("Demande d'ami envoyée");
                    request.setAttribute(SET_DIV, Alert.FRIENDREQUESTSENT);
                    GoToPage(request, response, ServletPage.AMIS);

                }else if(jsonObject.getString("message").equals("AcceptFriendRequest")) {
                    log.info("Demande d'ami acceptée");
                    Map<String, List<Map<String, Object>>> allAmisData = recupererAmisEtDemandes(jwt);
                    request.setAttribute("amisRequest", allAmisData.get("amisRequest"));
                    request.setAttribute("amisList", allAmisData.get("amisList"));
                    request.setAttribute(SET_DIV, Alert.ACCEPTFRIENDREQUEST);
                    GoToPage(request, response, ServletPage.AMIS);

                }else if(jsonObject.getString("message").equals("RefuseFriendRequest")) {
                    log.info("Demande d'ami refusée");
                    request.setAttribute(SET_DIV, Alert.REFUSEDFRIENDREQUEST);
                    Map<String, List<Map<String, Object>>> allAmisData = recupererAmisEtDemandes(jwt);
                    request.setAttribute("amisRequest", allAmisData.get("amisRequest"));
                    request.setAttribute("amisList", allAmisData.get("amisList"));
                    GoToPage(request, response, ServletPage.AMIS);

                } else {
                    log.error("Erreur lors de l'ajout d'ami");
                    request.setAttribute(SET_DIV, Alert.ERRORSERVER);
                    GoToPage(request, response, ServletPage.AMIS);

                }

            } else if (jsonObject.getString("status").equals("error")) {
                log.error("Erreur lors de l'ajout d'ami");
                request.setAttribute(SET_DIV, Alert.ERRORSERVER);
                GoToPage(request, response, ServletPage.AMIS);
            }
        }else{
            log.error("apiResponse est null, la récupération a échoué");
            request.setAttribute(SET_DIV, Alert.ERRORSERVER);
            GoToPage(request, response, ServletPage.AMIS);
        }
    }

    private Map<String, List<Map<String, Object>>> recupererAmisEtDemandes(String jwt) {
        Map<String, String> formData = new HashMap<>();
        formData.put("method", "GetListFriends");
        formData.put("jwt", jwt);

        Map<String, Object> amisJson = recuperationInfoAmis(formData, AMIS);

        String status = (String) amisJson.get("status");

        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        if (status.equals("success")) {
            // On récupère les listes d’amis et de demandes (déjà sous forme de List<Map<String, Object>>)
            List<Map<String, Object>> amisList = (List<Map<String, Object>>) amisJson.get("amis");
            List<Map<String, Object>> amisRequest = (List<Map<String, Object>>) amisJson.get("demandes");

            if (amisList == null) amisList = new ArrayList<>();
            if (amisRequest == null) amisRequest = new ArrayList<>();

            result.put("amisList", amisList);
            result.put("amisRequest", amisRequest);

        }else if(status.equals("error")) {
            log.warn("Échec de la récupération des amis : statut = {}", status);
            return null;
        }

        return result;
    }

    private Map<String, Object> ReponsePourLaDemandeAmis(String method, String jwt, String type, String action, String id) {
        Map<String, String> formData = new HashMap<>();
        formData.put("type", type);
        formData.put("action", action);
        formData.put("idGroupeMessagesPrivee", id);
        return envoyeLaDemandeAmis(method, jwt, formData, AMIS);
    }

}

