package org.esti.backend_esti.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto {
    private Long id;
    private String className;
    private String grade;
    private String section;
    private Integer academicYear;
} 