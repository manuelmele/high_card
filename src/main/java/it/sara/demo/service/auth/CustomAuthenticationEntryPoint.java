package it.sara.demo.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * This method is invoked when a user tries to access a secured resource without
     * proper authentication or provides invalid credentials.
     *
     * @param request       the HTTP request that resulted in an authentication failure
     * @param response      the HTTP response to return to the client
     * @param authException the exception thrown due to authentication failure
     * @throws IOException if there is an error writing the response
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        Map<String, Object> body = Map.of(
                "status", 401,
                "error", "Unauthorized",
                "message", "Token is missing or invalid"
        );

        response.getOutputStream().println(objectMapper.writeValueAsString(body));
    }
}
