package org.esti.backend_esti.Form;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Level;
import org.esti.backend_esti.Entity.SubjectTeacher;

@Data
@Builder
public class ClassForm {

    @Size(max = 100, message = "{El grado es requerido}")
    private Level level;

    @Size(max = 100, message = "{El grupo es requerido}")
    private Group group;

    @Size(max = 100, message = "{El profesor y materia son requeridos}")
    private SubjectTeacher subjectTeacher;


} 