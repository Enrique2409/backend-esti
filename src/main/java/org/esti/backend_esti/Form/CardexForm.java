package org.esti.backend_esti.Form;

import lombok.Data;

import java.io.Serializable;
@Data
public class CardexForm implements Serializable{

    private Long groupId;

    private Long teacherId;

    private Long studentId;

    private Long subjectId;

    private Integer firstPartial;

    private Integer secondPartial;

    private Integer thirdPartial;

    private Integer finalGrade;

}
