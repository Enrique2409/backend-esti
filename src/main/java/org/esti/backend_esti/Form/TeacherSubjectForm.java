package org.esti.backend_esti.Form;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherSubjectForm implements Serializable {

    private Long teacherId;

    private Long subjectId;
}
