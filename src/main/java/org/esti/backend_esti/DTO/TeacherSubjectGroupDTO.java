package org.esti.backend_esti.DTO;


import lombok.Builder;
import lombok.Data;
import org.esti.backend_esti.Entity.TeacherSubjectGroup;

@Data
@Builder
public class TeacherSubjectGroupDTO {

    private Long idTeacherSubjectGroup;

    private String groupName;
    private Integer grade;
    private String period;

    private Long idTeacher;
    private String teacherName;
    private String teacherLastName;

    private Long idSubject;
    private String subjectName;

    public static TeacherSubjectGroupDTO build(TeacherSubjectGroup teacherSubjectGroup) {
        return TeacherSubjectGroupDTO.builder()
                .idTeacherSubjectGroup(teacherSubjectGroup.getIdTeacherSubjectGroup())
                .groupName(teacherSubjectGroup.getGroup().getGroupName())
                .grade(teacherSubjectGroup.getGroup().getGrade())
                .period(teacherSubjectGroup.getGroup().getPeriod().getCve())
                .idTeacher(teacherSubjectGroup.getIdTeacherSubjectGroup())
                .teacherName(teacherSubjectGroup.getTeacher().getName())
                .teacherLastName(teacherSubjectGroup.getTeacher().getLastName())
                .idSubject(teacherSubjectGroup.getIdTeacherSubjectGroup())
                .subjectName(teacherSubjectGroup.getSubject().getName())
                .build();
    }
}
