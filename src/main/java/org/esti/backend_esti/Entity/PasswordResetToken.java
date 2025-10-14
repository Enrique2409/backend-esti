package org.esti.backend_esti.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "user_type", nullable = false)
    private String userType;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    @Builder
    public PasswordResetToken(String token, String email, String userType, LocalDateTime expiryDate) {
        this.token = token;
        this.email = email;
        this.userType = userType;
        this.expiryDate = expiryDate;
    }
}

