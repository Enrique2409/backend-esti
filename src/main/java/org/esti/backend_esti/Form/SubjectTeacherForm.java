package org.esti.backend_esti.Form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.esti.backend_esti.Entity.CourseClass;
import org.esti.backend_esti.Entity.Student;
import org.esti.backend_esti.Entity.Subject;
import org.esti.backend_esti.Entity.Teacher;

import java.io.Serializable;

@Data
public class SubjectTeacherForm implements Serializable {

    @NotNull(message = "El ID de la materia es requerido")
    private Subject subject;
    
    @NotNull(message = "El ID del profesor es requerido")
    private Teacher teacher;

    @NotNull(message = "El ID del estudiante es requerido")
    private Student student;

    @NotNull(message = "Es necesario el courseclass")
    private CourseClass courseClass;

} 