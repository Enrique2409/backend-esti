package org.esti.backend_esti.Form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.esti.backend_esti.Entity.Subject;
import org.esti.backend_esti.Entity.Teacher;

@Data
public class SubjectTeacherForm {

    @NotNull(message = "El ID de la materia es requerido")
    private Subject subject;
    
    @NotNull(message = "El ID del profesor es requerido")
    private Teacher teacher;

} 