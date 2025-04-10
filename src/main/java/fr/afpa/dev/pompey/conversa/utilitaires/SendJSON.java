package fr.afpa.dev.pompey.conversa.utilitaires;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class SendJSON {

    //Liste APIURL
    public static final String REGISTER = "user";
    public static final String LOGIN = "login";
    private SendJSON() {}


    public static JsonObject envoyerFormulaireVersApi(Map<String, String> formData, String apiUrl) {
        try {
            HttpURLConnection conn;
            // Construction du JSON à partir de la Map
            JSONObject json = new JSONObject(formData);
            // Connexion à l'API
            URL url = new URL("http://localhost:8080/ConversaAPI_war/"+apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            log.info(json.toString());
            conn.getOutputStream().write(json.toString().getBytes(StandardCharsets.UTF_8));

            // Vérifier si la réponse HTTP est un succès
            int responseCode = conn.getResponseCode();
            InputStream inputStream = (responseCode < 400)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            JsonObject jsonObject = Json.createReader(inputStream).readObject();

            log.info("Réponse JSON erreur : {}", jsonObject);

            return jsonObject;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
