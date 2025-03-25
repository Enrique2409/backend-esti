package org.esti.backend_esti.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Level;
import org.esti.backend_esti.Entity.Subject;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    private Long idSubject;
    private String name;
    private Level level;
    private Group group;

    public static SubjectDTO build(final Subject subject) {
        return SubjectDTO.builder()
                .idSubject(subject.getIdSubject())
                .name(subject.getName())
                .level(subject.getLevel())
                .group(subject.getGroup())
                .build();
    }
} 