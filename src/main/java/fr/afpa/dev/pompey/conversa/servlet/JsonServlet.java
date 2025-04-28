package fr.afpa.dev.pompey.conversa.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.afpa.dev.pompey.conversa.utilitaires.Alert;
import fr.afpa.dev.pompey.conversa.utilitaires.CookiesUtils;
import fr.afpa.dev.pompey.conversa.utilitaires.Page;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static fr.afpa.dev.pompey.conversa.utilitaires.SendJSON.*;

@Slf4j
@WebServlet(name = "JsonServlet", value = "/messageprivejson")
public class JsonServlet extends HttpServlet {

    @Override
    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> recupToutLesMessages = new HashMap<>();
        recupToutLesMessages.put("type", "getAllMessages");
        recupToutLesMessages.put("jwt", CookiesUtils.getJWT(request.getCookies()));
        Map<String, Object> apiResponse = getAllMessages(recupToutLesMessages, MESSAGEPRIVE);
        if (apiResponse != null && apiResponse.get("status").equals("success")) {
            log.info("apiResponse : OK " + apiResponse);
            List<Map<String, Object>> objects = (List<Map<String, Object>>) apiResponse.get("getAllMessages");

            // Envoyer les infos vers le web en json
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK); // Code 200 pour succès
            response.getWriter().write(objects.toString());
            log.info("getAllMessages : " + objects);
        }

    }
}