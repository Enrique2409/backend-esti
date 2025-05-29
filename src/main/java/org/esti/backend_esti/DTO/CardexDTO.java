package org.esti.backend_esti.DTO;

import lombok.Builder;
import lombok.Data;
import org.esti.backend_esti.Entity.Cardex;

import java.time.LocalDateTime;

@Data
@Builder
public class CardexDTO {

    private Long idCardex;

    private Long groupId;

    private Long teacherId;

    private Long studentId;

    private Long subjectId;

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
                .groupId(cardex.getGroup() != null ? cardex.getGroup().getIdGroup() : null)
                .teacherId(cardex.getTeacher() != null ? cardex.getTeacher().getIdTeacher() : null)
                .studentId(cardex.getStudent() != null ? cardex.getStudent().getIdStudent() : null)
                .subjectId(cardex.getSubject() != null ? cardex.getSubject().getIdSubject() : null)
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
