package org.esti.backend_esti.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esti.backend_esti.Entity.Teacher;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private Long idTeacher;

    private String name;

    private String lastName;

    private String phoneNumber;

    private String email;

    public static TeacherDTO build(final Teacher teacher) {
        return TeacherDTO.builder()
                .idTeacher(teacher.getIdTeacher())
                .name(teacher.getName())
                .lastName(teacher.getLastName())
                .phoneNumber(teacher.getPhoneNumber())
                .email(teacher.getEmail())
                .build();
    }
} 