package org.esti.backend_esti.Config.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.esti.backend_esti.Entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final String SECRET_KEY_STRING = "46557bdc6aff551c89de8c2823e1e05e21dbccf6eee4b9efea2a49cb73cbc9e1";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));

    public String generateToken(Long idUser, String username, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role.name());
        claims.put("id", idUser);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        logger.info("🎫 Generated token for user: {}, role: {}", username, role);
        return token;
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("❌ Error extracting claims from token: {}", e.getMessage());
            throw e;
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        Claims claims = extractClaims(token);
        return claims.get("role", String.class);
    }

    public boolean isTokenValid(String token, String username) {
        try {
            boolean valid = username.equals(extractUsername(token)) && !isTokenExpired(token);
            logger.info("🔍 Token validation for {}: {}", username, valid ? "VALID" : "INVALID");
            return valid;
        } catch (Exception e) {
            logger.error("❌ Token validation error: {}", e.getMessage());
            return false;
        }
    }

    public Long extractUserId(String token) {
        return extractClaims(token).get("id", Long.class);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractClaims(token).getExpiration();
        boolean expired = expiration.before(new Date());
        logger.info("📅 Token expiration: {}, Expired: {}", expiration, expired);
        return expired;
    }
}
