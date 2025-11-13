package org.esti.backend_esti.Form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardexForm implements Serializable{

    private Long studentId;

    private Long teacherSubjectId;

    private Long periodId;

    private Integer firstPartial;

    private Integer secondPartial;

    private Integer thirdPartial;

    private Integer finalGrade;

}
