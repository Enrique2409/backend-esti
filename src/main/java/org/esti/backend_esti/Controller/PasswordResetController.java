package org.esti.backend_esti.Controller;

import lombok.RequiredArgsConstructor;
import org.esti.backend_esti.DTO.PasswordResetDTO;
import org.esti.backend_esti.Form.ResetPasswordForm;
import org.esti.backend_esti.Service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/esti/authenticate")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<PasswordResetDTO> forgotPassword(@RequestParam String email) {
        PasswordResetDTO dto = passwordResetService.generateResetToken(email);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordForm form) {
        String mensaje = passwordResetService.resetPassword(form);
        return ResponseEntity.ok(mensaje);
    }
}
