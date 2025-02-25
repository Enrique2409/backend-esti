package org.esti.backend_esti.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectTeacherDto {
    private Long id;
    private Long subjectId;
    private Long teacherId;
    private Long classId;
} 