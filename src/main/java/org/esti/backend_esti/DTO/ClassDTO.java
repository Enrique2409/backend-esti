package org.esti.backend_esti.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esti.backend_esti.Entity.*;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassDTO {
    private Long id;
    private Level level;
    private Group group;
    private List<Student> students = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();

    public static ClassDTO build(final CourseClass classes) {
        return ClassDTO.builder()
                .id(classes.getIdClass())
                .level(classes.getLevel())
                .group(classes.getGroup())
                .students(classes.getStudents())
                .subjects(classes.getSubjects())
                .build();
    }
}