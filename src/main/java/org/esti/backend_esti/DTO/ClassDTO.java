package org.esti.backend_esti.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esti.backend_esti.Entity.CourseClass;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Level;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassDTO {
    private Long id;
    private Level level;
    private Group group;
    private List<StudentDTO> students;
    private List<SubjectDTO> subjects;

    public static ClassDTO build(final CourseClass classes) {
        return ClassDTO.builder()
                .id(classes.getIdClass())
                .level(classes.getLevel())
                .group(classes.getGroup())
                .students(
                        classes.getStudents().stream()
                                .map(StudentDTO::build)
                                .collect(Collectors.toList())
                )
                .subjects(
                        classes.getSubjects().stream()
                                .map(sub -> new SubjectDTO(sub.getIdSubject(), sub.getName()))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
