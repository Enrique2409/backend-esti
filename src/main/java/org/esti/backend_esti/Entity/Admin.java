package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.esti.backend_esti.Form.AdminForm;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "administrator", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin")
    private Long idAdmin;

    @Column (name = "name", length = 100, nullable = false)
    private String name;

    @Column (name = "last_name", length = 100, nullable = false)
    private String  relastName;

    @Column (name = "phone_number", length = 15)
    private String phoneNumber;

    @Column (name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    public Admin(final AdminForm form) {
        this.name = form.getName();
        this.lastName = form.getLastName();
        this.phoneNumber = form.getPhoneNumber();
        this.email = form.getEmail();
        this.password = form.getPassword();
    }

    public void updateAdmin(final AdminForm form) {
        if (form.getName() != null) {
            this.name = form.getName();
        }
        if (form.getLastName() != null) {
            this.lastName = form.getLastName();
        }
        if (form.getPhoneNumber() != null) {
            this.phoneNumber = form.getPhoneNumber();
        }
        if (form.getEmail() != null) {
            this.email = form.getEmail();
        }
        if (form.getPassword() != null) {
            this.password = form.getPassword();
        }
    }

}
