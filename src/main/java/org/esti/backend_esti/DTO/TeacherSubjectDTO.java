package org.esti.backend_esti.DTO;


import lombok.Builder;
import lombok.Data;
import org.esti.backend_esti.Entity.TeacherSubject;

@Data
@Builder
public class TeacherSubjectDTO {

    private Long idTeacherSubject;

    private String groupName;
    private Integer grade;
    private String period;

    private Long idTeacher;
    private String teacherName;
    private String teacherLastName;

    private Long idSubject;
    private String subjectName;

    public static TeacherSubjectDTO build(TeacherSubject teacherSubject) {
        return TeacherSubjectDTO.builder()
                .idTeacherSubject(teacherSubject.getIdTeacherSubject())
                .idTeacher(teacherSubject.getIdTeacherSubject())
                .teacherName(teacherSubject.getTeacher().getName())
                .teacherLastName(teacherSubject.getTeacher().getLastName())
                .idSubject(teacherSubject.getIdTeacherSubject())
                .subjectName(teacherSubject.getSubject().getName())
                .build();
    }
}
