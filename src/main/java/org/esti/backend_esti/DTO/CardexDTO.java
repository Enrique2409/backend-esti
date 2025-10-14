package org.esti.backend_esti.DTO;

import lombok.Builder;
import lombok.Data;
import org.esti.backend_esti.Entity.*;

import java.time.LocalDateTime;

@Data
@Builder
public class CardexDTO {

    private Long idCardex;

    private Long idGroup;
    private String groupName;
    private Integer grade;
    private String period;

    private Long idTeacher;
    private String teacherName;
    private String teacherLastName;

    private Long idStudent;
    private String studentName;
    private String studentLastNamePaternal;
    private String studentLastNameMaternal;

    private Long idSubject;
    private String subjectName;

    private Integer firstPartial;

    private Integer secondPartial;

    private Integer thirdPartial;

    private Integer finalGrade;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public static CardexDTO build(final Cardex cardex) {
        return CardexDTO.builder()
                .idCardex(cardex.getIdCardex())
                .idGroup(cardex.getTeacherSubjectGroup().getGroup().getIdGroup())
                .groupName(cardex.getTeacherSubjectGroup().getGroup().getGroupName())
                .grade(cardex.getTeacherSubjectGroup().getGroup().getGrade())
                .period(cardex.getTeacherSubjectGroup().getGroup().getPeriod().getCve())
                .idTeacher(cardex.getTeacherSubjectGroup().getTeacher().getIdTeacher())
                .teacherName(cardex.getTeacherSubjectGroup().getTeacher().getName())
                .teacherLastName(cardex.getTeacherSubjectGroup().getTeacher().getLastName())
                .idStudent(cardex.getStudent().getIdStudent())
                .studentName(cardex.getStudent().getName())
                .studentLastNamePaternal(cardex.getStudent().getLastNamePaternal())
                .studentLastNameMaternal(cardex.getStudent().getLastNameMaternal())
                .idSubject(cardex.getTeacherSubjectGroup().getSubject().getIdSubject())
                .subjectName(cardex.getTeacherSubjectGroup().getSubject().getName())
                .firstPartial(cardex.getFirstPartial())
                .secondPartial(cardex.getSecondPartial())
                .thirdPartial(cardex.getThirdPartial())
                .finalGrade(cardex.getFinalGrade())
                .createdAt(cardex.getCreatedAt())
                .updatedAt(cardex.getUpdatedAt())
                .deletedAt(cardex.getDeletedAt())
                .build();
    }
}
