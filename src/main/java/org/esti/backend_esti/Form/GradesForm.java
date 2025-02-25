package org.esti.backend_esti.Form;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@Data
public class GradesForm {
    @NotNull(message = "La calificación es requerida")
    @Min(value = 0, message = "La calificación no puede ser menor a 0")
    @Max(value = 100, message = "La calificación no puede ser mayor a 100")
    private Double score;
    
    @NotNull(message = "El término es requerido")
    private String term;
    
    @NotNull(message = "El ID del estudiante es requerido")
    private Long studentId;
    
    @NotNull(message = "El ID de la materia es requerido")
    private Long subjectId;
} 