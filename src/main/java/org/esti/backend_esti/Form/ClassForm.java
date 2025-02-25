package org.esti.backend_esti.Form;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

@Data
public class ClassForm {
    @NotBlank(message = "El nombre de la clase es requerido")
    private String className;
    
    @NotBlank(message = "El grado es requerido")
    private String grade;
    
    @NotBlank(message = "La sección es requerida")
    private String section;
    
    @NotNull(message = "El año académico es requerido")
    @Min(value = 2000, message = "El año académico debe ser válido")
    private Integer academicYear;
} 