package org.esti.backend_esti.Form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Level;

@Data
public class SubjectForm {

    @NotBlank(message = "El nombre de la materia es requerido")
    private String name;
    
    private Level level;

    private Group group;
} 