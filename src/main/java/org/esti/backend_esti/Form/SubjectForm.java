package org.esti.backend_esti.Form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectForm implements Serializable {

    @NotBlank(message = "El nombre de la materia es requerido")
    private String name;

    private String description;


} 