package fr.afpa.dev.pompey.conversa.securite;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebFilter("*")
@Slf4j
public class CSRFTokenFilter implements Filter {
    private static final String CSRFTOKEN = "csrfToken";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        // Convertir en HttpServletRequest/HttpServletResponse
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String contextPath = httpRequest.getContextPath();
        String requestURI = httpRequest.getRequestURI();
        if (requestURI.contains(".")) {
            chain.doFilter(request, response);
        } else {
            List<String> routesAProteger = List.of(
                    contextPath + "/register",
                    contextPath + "/login",
                    contextPath + "/",
                    contextPath + "/amis",
                    contextPath + "/messageprive",
                    contextPath + "/admin"
            );

            log.info("filtre csrf appele");
            HttpSession session = httpRequest.getSession(false);

            // Vérifier uniquement pour les requêtes sensibles (POST, PUT, DELETE)
            String method = httpRequest.getMethod();
            log.info(method);
            if (session != null
                    && (method.equalsIgnoreCase("POST")
                    || method.equalsIgnoreCase("PUT")
                    || method.equalsIgnoreCase("DELETE"))
                    && routesAProteger.contains(requestURI)) {
                // Récupérer le token CSRF envoyé par le client
                log.info("REQUETE: {} ", requestURI);
                request.getParameterMap().forEach((key, value) -> log.info("REQUETE PARAMETER key: {} value: {}", key, value));
                String csrfTokenFromClient = httpRequest.getParameter(CSRFTOKEN);

                // Récupérer le token CSRF stocké dans la session
                String csrfTokenFromServer = (String) session.getAttribute(CSRFTOKEN);
                log.info("filtre csrf verification");
                log.info("csrf requete: {}", csrfTokenFromClient);
                log.info("csrf session: {}", csrfTokenFromServer);
                // Validation
                if (csrfTokenFromClient == null || !csrfTokenFromClient.equals(csrfTokenFromServer)) {
                    // Rejet si le token est invalide ou absent
                    log.info("filtre csrf invalide ou absent");
                    if (!httpResponse.isCommitted()) {
                        // Envoyer une réponse d'erreur 403 Forbidden
                        httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
                    } else {
                        // Si la réponse est déjà engagée, réinitialisez la réponse
                        httpResponse.reset();
                        httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
                    }
                    return;
                }
            } else if (session != null && (method.equalsIgnoreCase("GET") && routesAProteger.contains(requestURI))) {
                log.info("request uri : {}", requestURI);
                String uuidStr = UUID.randomUUID().toString();
                session.setAttribute(CSRFTOKEN, uuidStr);
                request.setAttribute(CSRFTOKEN, uuidStr);
                log.info("csrf token : {}", uuidStr);
            }
            chain.doFilter(request, response);
        }
    }
}
