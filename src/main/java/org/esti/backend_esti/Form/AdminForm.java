package org.esti.backend_esti.Form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AdminForm implements Serializable {

    @Size(max = 100, message = "{name.right.lenght}")
    private String name;

    @Size(max = 100, message = "{lastName.right.lenght}")
    private String lastName;

    @Size(max = 15, message = "{phoneNumber.right.lenght}")
    private String phoneNumber;

    @Email(message = "{wrong.email.structure}")
    private String email;

    private String password;
}
