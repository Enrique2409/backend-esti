package org.esti.backend_esti.Form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.esti.backend_esti.Entity.CourseClass;


import java.io.Serializable;
import java.time.LocalDate;

@Data
public class StudentForm implements Serializable {

    @NotBlank(message = "El nombre es requerido")
    private String name;

    @NotBlank(message = "El apellido paterno es requerido")
    private String lastNamePaternal;

    @NotBlank(message = "El apellido materno es requerido")
    private String lastNameMaternal;

    @Pattern(regexp = "^[0-9]{10}$", message = "El número telefónico debe tener 10 dígitos")
    private String phoneNumber;

    @NotBlank(message = "La curp no puede estar vacía")
    private String curp;

    private LocalDate birthDate;
} 