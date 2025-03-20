package org.esti.backend_esti.Form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Level;

import java.io.Serializable;


@Data
public class TeacherForm implements Serializable {
    @NotBlank(message = "El nombre es requerido")
    private String name;
    
    @NotBlank(message = "El apellido es requerido")
    private String lastName;
    
    @NotBlank(message = "El email es requerido")
    @Email(message = "El formato del email no es válido")
    private String email;
    
    @Pattern(regexp = "^[0-9]{10}$", message = "El número telefónico debe tener 10 dígitos")
    private String phoneNumber;
    
    private String password;

    private Level level;

    private Group group;
} 