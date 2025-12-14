package org.esti.backend_esti.Config.Jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        logger.info("🔍 Processing request to: {}", path);

        if (path.equals("/esti/auth/login")) {
            logger.info("✅ Login endpoint - skipping JWT validation");
            chain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        logger.info("📋 Authorization header: {}", authorizationHeader != null ? "Present" : "Missing");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring(7);
                logger.info("🎫 Token extracted (length: {})", token.length());

                String username = jwtUtil.extractUsername(token);
                logger.info("👤 Username from token: {}", username);

                if (jwtUtil.isTokenValid(token, username)) {
                    Long userId = jwtUtil.extractUserId(token);
                    String role = jwtUtil.extractRole(token);

                    logger.info("✅ Token valid - User: {}, Role: {}, ID: {}", username, role, userId);

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username, null, Collections.singleton(() -> "ROLE_" + role));

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    logger.info("🔐 Authentication set in SecurityContext");
                } else {
                    logger.warn("❌ Token validation failed for user: {}", username);
                }
            } catch (Exception e) {
                logger.error("❌ Error processing JWT token: {}", e.getMessage(), e);
            }
        } else {
            logger.warn("⚠️ No valid Authorization header found");
        }

        chain.doFilter(request, response);
    }
}
