package org.esti.backend_esti.Form;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherSubjectGroupForm implements Serializable {

    private Long groupId;

    private Long teacherId;

    private Long subjectId;
}
