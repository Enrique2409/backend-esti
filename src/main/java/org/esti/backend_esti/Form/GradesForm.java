package org.esti.backend_esti.Form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.esti.backend_esti.Entity.Student;
import org.esti.backend_esti.Entity.SubjectTeacherClass;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class GradesForm implements Serializable {

    @NotNull(message = "La calificación es requerida")
    @Min(value = 0, message = "La calificación no puede ser menor a 0")
    @Max(value = 100, message = "La calificación no puede ser mayor a 100")
    private Double grade;

    @NotNull(message = "La fecha de evaluación es requerida")
    private LocalDateTime evaluationDate;
    
    @NotNull(message = "El ID del estudiante es requerido")
    private Student student;
    
    @NotNull(message = "El ID de la materia es requerido")
    private SubjectTeacherClass subjectTeacher;
} 