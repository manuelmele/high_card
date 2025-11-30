package it.sara.demo.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Handles access denied exceptions by returning a JSON response with a 403 Forbidden status
     * and a message indicating that access is restricted to the ADMIN role.
     *
     * @param request                the HTTP request that resulted in a denied exception
     * @param response               the HTTP response to be sent back to the client
     * @param accessDeniedException  the access denied exception that occurred
     * @throws IOException           if an I/O error occurs while writing the response
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        Map<String, Object> data = Map.of(
                "status", 403,
                "error", "Forbidden",
                "message", "Only ADMIN role is allowed for this resource"
        );

        response.getOutputStream().println(objectMapper.writeValueAsString(data));
    }
}