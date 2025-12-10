package org.esti.backend_esti.Controller;

import lombok.RequiredArgsConstructor;
import org.esti.backend_esti.Config.Jwt.JwtUtil;
import org.esti.backend_esti.DTO.LockedResponse;
import org.esti.backend_esti.DTO.SystemConfigDTO;
import org.esti.backend_esti.Form.LockedGradesForm;
import org.esti.backend_esti.Service.SystemConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("esti/admin/system")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SystemConfigController {

    private final SystemConfigService systemConfigService;
    private final JwtUtil jwtUtil;

    @GetMapping("/config")
    public ResponseEntity<SystemConfigDTO> getConfiguration() {
        return ResponseEntity.ok(systemConfigService.getConfiguration());
    }

    @PostMapping("/cardex/lock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LockedResponse> changeStatusGrades(
            @RequestBody LockedGradesForm form,
            HttpServletRequest request) {

        Long adminId = extractAdminIdFromToken(request);

        if (adminId == null) {
            System.err.println("No se pudo obtener el ID del administrador del token");
            return ResponseEntity.badRequest()
                    .body(new LockedResponse(false, "No se pudo identificar al administrador", null));
        }

        System.out.println("Admin ID obtenido del token: " + adminId);

        LockedResponse response = systemConfigService.changeStatusGrades(form, adminId);
        return ResponseEntity.ok(response);
    }

    private Long extractAdminIdFromToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.err.println("Authorization header missing or invalid");
                return null;
            }

            String token = authHeader.substring(7);
            Long userId = jwtUtil.extractUserId(token);

            System.out.println("ID extraído del token: " + userId);
            return userId;

        } catch (Exception e) {
            System.err.println("Error al extraer ID del token: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}