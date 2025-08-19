package org.esti.backend_esti.DTO;

import lombok.Builder;
import lombok.Data;
import org.esti.backend_esti.Entity.*;

import java.time.LocalDateTime;

@Data
@Builder
public class CardexDTO {

    private Long idCardex;

    private String groupName;
    private Integer grade;
    private String period;

    private String teacherName;
    private String teacherLastName;

    private String studentName;
    private String studentLastNamePaternal;
    private String studentLastNameMaternal;

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
                .groupName(cardex.getGroup().getGroupName())
                .grade(cardex.getGroup().getGrade())
                .period(cardex.getGroup().getPeriod().getCve())
                .teacherName(cardex.getTeacher().getName())
                .teacherLastName(cardex.getTeacher().getLastName())
                .studentName(cardex.getStudent().getName())
                .studentLastNamePaternal(cardex.getStudent().getLastNamePaternal())
                .studentLastNameMaternal(cardex.getStudent().getLastNameMaternal())
                .subjectName(cardex.getSubject().getName())
                .firstPartial(cardex.getFirstPartial())
                .secondPartial(cardex.getSecondPartial())
                .thirdPartial(cardex.getThirdPartial())
                .finalGrade(cardex.getFinal_grade())
                .createdAt(cardex.getCreatedAt())
                .updatedAt(cardex.getUpdatedAt())
                .deletedAt(cardex.getDeletedAt())
                .build();
    }
}
