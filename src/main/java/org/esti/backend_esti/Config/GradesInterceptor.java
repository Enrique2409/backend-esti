package org.esti.backend_esti.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.esti.backend_esti.Service.SystemConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GradesInterceptor implements HandlerInterceptor {

    private final SystemConfigService systemConfigService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        System.out.println("=== INTERCEPTOR DEBUG ===");
        System.out.println("URI: " + uri);
        System.out.println("Method: " + method);

        if (uri.contains("/esti/cardex") &&
                (method.equals("POST") || method.equals("PATCH") || method.equals("DELETE"))) {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null && auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"))) {
                System.out.println("Authentication: " + auth);
                System.out.println("Principal: " + auth.getPrincipal());
                System.out.println("Authorities: " + auth.getAuthorities());
                boolean isTeacher = auth.getAuthorities().stream()
                        .anyMatch(a -> {
                            String authority = a.getAuthority();
                            System.out.println("  Checking authority: " + authority);
                            return authority.equals("ROLE_TEACHER");
                        });

                System.out.println("Is Teacher: " + isTeacher);

                if (isTeacher) {
                    boolean gradesLocked = systemConfigService.areLockedGrades();
                    System.out.println("Grades Locked: " + gradesLocked);

                    if (gradesLocked) {
                        System.out.println("❌ ACCESO DENEGADO - Calificaciones bloqueadas");
                        sendErrorResponse(response);
                        return false;
                    } else {
                        System.out.println("✅ Calificaciones desbloqueadas - Permitiendo acceso");
                    }
                } else {
                    System.out.println("✅ Usuario NO es TEACHER - Permitiendo acceso");
                }
            } else {
                System.out.println("⚠️ Authentication is null");
            }
        } else {
            System.out.println("⏭️ URI o método no coinciden - Saltando interceptor");
        }

        /*return true;
                if (systemConfigService.areLockedGrades()) {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(
                            "{\"error\":\"El periodo de calificaciones ha sido cerrado por administración\"}"
                    );
                    return false;
                }*/


        return true;
    }

    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                "{\"error\":\"El periodo de calificaciones ha sido cerrado por administración\", \"status\":403}"
        );
    }
}
