package org.esti.backend_esti.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esti.backend_esti.Entity.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradesDTO {

    private Long idGrade;

    private Double grade;

    private LocalDateTime evaluationDate;

    private Student student;

    private SubjectTeacher subjectTeacher;

    public static GradesDTO build(final Grades grades) {
        return GradesDTO.builder()
                .idGrade(grades.getIdGrade())
                .grade(grades.getGrade())
                .evaluationDate(grades.getEvaluationDate())
                .student(grades.getStudent())
                .subjectTeacher(grades.getSubjectTeacher())
                .build();
    }
} 