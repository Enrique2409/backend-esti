package org.esti.backend_esti.Form;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class TeacherForm {
    @NotBlank(message = "El nombre es requerido")
    private String firstName;
    
    @NotBlank(message = "El apellido es requerido")
    private String lastName;
    
    @NotBlank(message = "El email es requerido")
    @Email(message = "El formato del email no es válido")
    private String email;
    
    @Pattern(regexp = "^[0-9]{10}$", message = "El número telefónico debe tener 10 dígitos")
    private String phoneNumber;
    
    @NotBlank(message = "La especialización es requerida")
    private String specialization;
} 