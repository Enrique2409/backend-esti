package org.esti.backend_esti.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esti.backend_esti.Entity.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassDTO {
    private Long id;
    private Level level;
    private Group group;
    private SubjectTeacher subjectTeacher;

    public static ClassDTO build(final CourseClass classes) {
        return ClassDTO.builder()
                .id(classes.getIdClass())
                .level(classes.getLevel())
                .group(classes.getGroup())
                .subjectTeacher(classes.getSubjectTeacher())
                .build();
    }
}