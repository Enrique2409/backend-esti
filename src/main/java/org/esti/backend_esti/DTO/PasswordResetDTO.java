package org.esti.backend_esti.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PasswordResetDTO {

    private String email;
    private String userType;
    private String message;
}
