package org.esti.backend_esti.Form;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class SubjectTeacherForm {
    @NotNull(message = "El ID de la materia es requerido")
    private Long subjectId;
    
    @NotNull(message = "El ID del profesor es requerido")
    private Long teacherId;
    
    @NotNull(message = "El ID de la clase es requerido")
    private Long classId;
} 