package it.sara.demo.service.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import static it.sara.demo.web.user.request.LoginRequest.Role.ADMIN;
import static it.sara.demo.web.user.request.LoginRequest.Role.USER;

@Component
public class JwtUtil {

    // Secret key
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // Token issuer
    private final String ISSUER = "my-app";

    // Generate JWT token
    public String generateToken(String username, Map<String, Object> claims) {
        // Token validity in milliseconds
        // 1 hour
        long validity = 3600000;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key)
                .compact();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();

            // Check issuer
            if (!ISSUER.equals(claims.getIssuer())) {
                return false;
            }

            // Check expiration
            if (claims.getExpiration().before(new Date())) {
                return false;
            }

            // Check authorization/policy (example: role)
            String role = (String) claims.get("role");
            return ADMIN.getValue().equals(role) || USER.getValue().equals(role);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
