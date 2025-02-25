package org.esti.backend_esti.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradesDto {
    private Long id;
    private Double score;
    private String term;
    private Long studentId;
    private Long subjectId;
} 