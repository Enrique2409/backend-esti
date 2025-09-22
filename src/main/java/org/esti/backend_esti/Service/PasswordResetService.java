package org.esti.backend_esti.Service;

import lombok.RequiredArgsConstructor;
import org.esti.backend_esti.DTO.PasswordResetDTO;
import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Entity.PasswordResetToken;
import org.esti.backend_esti.Entity.Teacher;
import org.esti.backend_esti.Form.ResetPasswordForm;
import org.esti.backend_esti.Repository.AdminRepository;
import org.esti.backend_esti.Repository.PasswordResetTokenRepository;
import org.esti.backend_esti.Repository.TeacherRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public PasswordResetDTO generateResetToken(String email) {
        String userType;

        if (adminRepository.findByEmail(email).isPresent()) {
            userType = "ADMIN";
        } else if (teacherRepository.findByEmail(email).isPresent()) {
            userType = "TEACHER";
        } else {
            throw new IllegalArgumentException("No existe un usuario con ese correo");
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .email(email)
                .userType(userType)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();

        tokenRepository.save(resetToken);

        String resetLink = "http://localhost:3000/password/reset-password?token=" + token;

        try {
            emailService.sendPasswordResetEmail(email, resetLink);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo de recueración", e);
        }

        return PasswordResetDTO.builder()
                .email(email)
                .userType(userType)
                .message("Se envió un correo con instrucciones de recuperación")
                .build();
    }

    public String resetPassword(ResetPasswordForm form) {
        PasswordResetToken resetToken = tokenRepository.findByToken(form.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken);
            throw new IllegalArgumentException("El token ha expirado");
        }

        String email = resetToken.getEmail();

        if ("ADMIN".equals(resetToken.getUserType())) {
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("Admin no encontrado"));
            admin.setPassword(passwordEncoder.encode(form.getNewPassword()));
            adminRepository.save(admin);
        } else {
            Teacher teacher = teacherRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("Teacher no encontrado"));
            teacher.setPassword(passwordEncoder.encode(form.getNewPassword()));
            teacherRepository.save(teacher);
        }

        tokenRepository.delete(resetToken);

        return "Contraseña actualizada con éxito";
    }
}
