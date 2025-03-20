package org.esti.backend_esti.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectTeacherDTO {
    private Long id;
    private Long subjectId;
    private Long teacherId;
    private Long classId;
} 