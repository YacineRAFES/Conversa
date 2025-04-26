package fr.afpa.dev.pompey.conversa.utilitaires;

import jakarta.json.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SendJSON {

    public static final String REGISTER = "user";
    public static final String LOGIN = "login";
    public static final String AMIS = "amis";
    public static final String MESSAGEPRIVE = "MessagesPrivee";

    private SendJSON() {}

    public static Map<String, Object> getAllMessages(Map<String, String> formData, String apiUrl){
        log.info("Fonction getAllMessages appelée avec les paramètres : formData = {}, apiUrl = {}", formData, apiUrl);

        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://localhost:8080/ConversaAPI_war/" + apiUrl);
            conn = (HttpURLConnection) url.openConnection();

            // Configuration de la connexion
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("objects", new JSONObject(formData));

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }
            log.info("getAllMessages: JSON envoyé : {}", json);

            JsonObject fullResponse = lireReponseJSON(conn);

            log.info("getAllMessages: Réponse JSON : {}", fullResponse);

            JsonObject objects = fullResponse.getJsonObject("objects");

            Map<String, Object> result = new HashMap<>();
            result.put("AllMessages", objects.getJsonArray("AllMessages"));
            return result;

        } catch (IOException e) {
            log.error("getAllMessages: Erreur lors de l'envoi du formulaire à l'API : {}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    public static Map<String, JsonArray> rechercherUnAmis(String method, Map<String, String> formData, String apiUrl) {
        log.info("Fonction rechercherUnAmis appelée avec les paramètres : method = {}, formData = {}, apiUrl = {}", method, formData, apiUrl);
        HttpURLConnection conn = null;

        try {
            URL url = new URL("http://localhost:8080/ConversaAPI_war/" + apiUrl);
            conn = (HttpURLConnection) url.openConnection();

            // Configuration de la connexion
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("method", method);
            json.put("objects", new JSONObject(formData));

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }
            log.info("rechercherUnAmis: JSON envoyé : {}", json);

            JsonObject fullResponse = lireReponseJSON(conn);

            log.info("rechercherUnAmis: Réponse JSON : {}", fullResponse);

            JsonObject objects = fullResponse.getJsonObject("objects");

            Map<String, JsonArray> result = new HashMap<>();
            result.put("amisRechercher", objects.getJsonArray("amisRechercher"));
            return result;

        } catch (IOException e) {
            log.error("rechercherUnAmis: Erreur lors de l'envoi du formulaire à l'API : {}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    public static Map<String, Object> recuperationInfoAmis(String method, Map<String, String> formData, String apiUrl) {
        HttpURLConnection conn = null;

        try {
            URL url = new URL("http://localhost:8080/ConversaAPI_war/" + apiUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("method", method);
            json.put("objects", new JSONObject(formData));

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }
            log.info("recuperationInfoAmis: JSON envoyé : {}", json);

            JsonObject fullResponse = lireReponseJSON(conn);

            log.info("recuperationInfoAmis: Réponse JSON : {}", fullResponse);
            log.info("Statut de la réponse: {}", fullResponse.getString("status"));

            JsonObject objects = fullResponse.getJsonObject("objects");

            Map<String, Object> result = new HashMap<>();
            result.put("status", fullResponse.getString("status"));
            result.put("amis", objects.getJsonArray("amis"));
            result.put("demandes", objects.getJsonArray("demandes"));
            return result;

        } catch (IOException e) {
            log.error("recuperationInfoAmis: Erreur lors de l'envoi du formulaire à l'API : {}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    public static Map<String, Object> envoyeLaDemandeAmis(String method, Map<String, String> formData, String apiUrl) {
        HttpURLConnection conn = null;

        try {
            URL url = new URL("http://localhost:8080/ConversaAPI_war/" + apiUrl);
            conn = (HttpURLConnection) url.openConnection();

            // Configuration de la connexion
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("method", method);
            json.put("objects", new JSONObject(formData));

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }
            log.info("envoyeLaDemandeAmis: JSON envoyé : {}", json);

            JsonObject fullResponse = lireReponseJSON(conn);

            log.info("envoyeLaDemandeAmis: Réponse JSON : {}", fullResponse);

            Map<String, Object> result = new HashMap<>();
            result.put("json", fullResponse);
            return result;

        } catch (IOException e) {
            log.error("envoyeLaDemandeAmis: Erreur lors de l'envoi du formulaire à l'API : {}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    public static Map<String, Object> envoyerFormulaireVersApi(Map<String, String> formData, String apiUrl) {
        HttpURLConnection conn = null;

        try {
            JSONObject json = new JSONObject(formData);
            URL url = new URL("http://localhost:8080/ConversaAPI_war/" + apiUrl);
            conn = (HttpURLConnection) url.openConnection();

            // Configuration de la connexion
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Envoi du JSON
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }

            // Lecture de la réponse JSON (succès ou erreur)
            JsonObject jsonObject = lireReponseJSON(conn);

            String autorizationHeader = conn.getHeaderField("Authorization");

            log.info("envoyerFormulaireVersApi: Réponse JSON : {}", jsonObject);
            log.info("envoyerFormulaireVersApi: AutorizationHeader : {}", autorizationHeader);

            Map<String, Object> result = new HashMap<>();
            result.put("json", jsonObject);
            result.put("Authorization", autorizationHeader);
            return result;

        } catch (IOException e) {
            log.error("envoyerFormulaireVersApi: Erreur lors de l'envoi du formulaire à l'API : {}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    private static JsonObject lireReponseJSON(HttpURLConnection conn) throws IOException {
        InputStream inputStream;

        try {
            inputStream = conn.getInputStream(); // Réponse 2xx
        } catch (IOException e) {
            inputStream = conn.getErrorStream(); // Réponse 4xx ou 5xx
        }

        if (inputStream != null) {
            return Json.createReader(inputStream).readObject();
        } else {
            return Json.createObjectBuilder()
                    .add("message", "Aucune réponse reçue de l'API")
                    .build();
        }
    }
}
