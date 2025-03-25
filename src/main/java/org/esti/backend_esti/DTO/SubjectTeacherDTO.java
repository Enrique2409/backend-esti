package org.esti.backend_esti.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Entity.Subject;
import org.esti.backend_esti.Entity.SubjectTeacher;
import org.esti.backend_esti.Entity.Teacher;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectTeacherDTO {

    private Long idSubjectTeacher;

    private Subject subject;

    private Teacher teacher;

    public static SubjectTeacherDTO build(final SubjectTeacher subjectTeacher) {
        return SubjectTeacherDTO.builder()
                .idSubjectTeacher(subjectTeacher.getIdSubjectTeacher())
                .subject(subjectTeacher.getSubject())
                .teacher(subjectTeacher.getTeacher())
                .build();
    }
} 