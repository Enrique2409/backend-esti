package org.esti.backend_esti.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esti.backend_esti.Entity.CourseClass;
import org.esti.backend_esti.Entity.Subject;
import org.esti.backend_esti.Entity.SubjectTeacherClass;
import org.esti.backend_esti.Entity.Teacher;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectTeacherDTO {

    private Long idSubjectTeacher;

    private Subject subject;

    private Teacher teacher;

    private CourseClass courseClass;

    public static SubjectTeacherDTO build(final SubjectTeacherClass subjectTeacher) {
        return SubjectTeacherDTO.builder()
                .idSubjectTeacher(subjectTeacher.getIdSubjectTeacherClass())
                .subject(subjectTeacher.getSubject())
                .teacher(subjectTeacher.getTeacher())
                .courseClass(subjectTeacher.getCourseClass())
                .build();
    }
} 