package org.esti.backend_esti.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esti.backend_esti.Entity.Subject;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    private Long idSubject;
    private String name;
    private String description;

    public static SubjectDTO build(final Subject subject) {
        return SubjectDTO.builder()
                .idSubject(subject.getIdSubject())
                .name(subject.getName())
                .description(subject.getDescription())
                .build();
    }
}
