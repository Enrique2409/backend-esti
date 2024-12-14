package org.esti.backend_esti.DTO;

import lombok.Builder;
import lombok.Data;
import org.esti.backend_esti.Entity.Admin;

import java.time.LocalDate;

@Data
@Builder
public class AdminDTO {

    private Long idAdmin;

    private String name;

    private String lastName;

    private String phoneNumber;

    private String email;

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
