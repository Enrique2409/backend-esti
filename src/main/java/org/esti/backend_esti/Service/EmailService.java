package org.esti.backend_esti.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String resetLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("e.kikerade@gmail.com");
        helper.setTo(to);
        helper.setSubject("Restablecer tu contraseña");

        String htmlContent = """
                <div>
                    <p>Hola,</p>
                                <p>Has solicitado restablecer tu contraseña. Haz clic en el siguiente botón:</p>
                                <a href="%s" style="display:inline-block;padding:10px 20px;
                                    font-size:16px;color:#fff;background-color:#007BFF;
                                    text-decoration:none;border-radius:5px;">
                                    Restablecer Contraseña
                                </a>
                    <p>Si no solicitaste este cambio, ignora este correo.</p>
                </div>
                """.formatted(resetLink);

        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}
