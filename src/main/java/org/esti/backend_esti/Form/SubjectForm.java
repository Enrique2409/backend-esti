package org.esti.backend_esti.Form;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

@Data
public class SubjectForm {
    @NotBlank(message = "El nombre de la materia es requerido")
    private String subjectName;
    
    @NotBlank(message = "La descripción es requerida")
    private String description;
    
    @NotNull(message = "Los créditos son requeridos")
    @Min(value = 1, message = "Los créditos deben ser al menos 1")
    private Integer credits;
} 