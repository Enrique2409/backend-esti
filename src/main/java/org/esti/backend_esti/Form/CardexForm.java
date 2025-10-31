package org.esti.backend_esti.Form;

import lombok.Data;

import java.io.Serializable;
@Data
public class CardexForm implements Serializable{

    private Long studentId;

    private Long teacherSubjectId;

    private Long periodId;

    private Integer firstPartial;

    private Integer secondPartial;

    private Integer thirdPartial;

    private Integer finalGrade;

}
