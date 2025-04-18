package fr.afpa.dev.pompey.conversa.utilitaires;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
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

    private SendJSON() {}

    public static JsonArray recupererArray(Map<String, String> formData, String apiUrl) {
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

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStream is = conn.getInputStream();
                     JsonReader reader = Json.createReader(is)) {
                    return reader.readArray(); // <- on lit le JsonArray ici
                }
            } else {
                log.error("Réponse non OK : " + responseCode);
            }

        } catch (IOException e) {
            log.error("Erreur lors de la connexion à l'API : {}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    public static Map<String, JsonArray> envoyerFormulaireVersApiAvecObjects(Map<String, String> formData, String apiUrl) {
        HttpURLConnection conn = null;

        try {
            JSONObject json = new JSONObject(formData);
            URL url = new URL("http://localhost:8080/ConversaAPI_war/" + apiUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }

            JsonObject fullResponse = lireReponseJSON(conn);

            log.info("Réponse JSON : {}", fullResponse);

            JsonObject objects = fullResponse.getJsonObject("objects");

            Map<String, JsonArray> result = new HashMap<>();
            result.put("amis", objects.getJsonArray("amis"));
            result.put("demandes", objects.getJsonArray("demandes"));
            return result;

        } catch (IOException e) {
            log.error("Erreur lors de l'envoi du formulaire à l'API : {}", e.getMessage());
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

            log.info("Réponse JSON : {}", jsonObject);
            log.info("AutorizationHeader : {}", autorizationHeader);

            Map<String, Object> result = new HashMap<>();
            result.put("json", jsonObject);
            result.put("Authorization", autorizationHeader);
            return result;

        } catch (IOException e) {
            log.error("Erreur lors de l'envoi du formulaire à l'API : {}", e.getMessage());
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
