package org.esti.backend_esti.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esti.backend_esti.Entity.Admin;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradesDTO {
    private Long id;
    private Double score;
    private String term;
    private Long studentId;
    private Long subjectId;

    public static AdminDTO build(final Admin admin) {
        return AdminDTO.builder()
                .idAdmin(admin.getIdAdmin())
                .name(admin.getName())
                .lastName(admin.getLastName())
                .phoneNumber(admin.getPhoneNumber())
                .email(admin.getEmail())
                .build();
    }
} 