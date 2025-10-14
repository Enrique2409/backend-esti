package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.esti.backend_esti.Form.TeacherForm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teacher", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_teacher")
    private Long idTeacher;

    @Column (name = "name", length = 100, nullable = false)
    private String name;

    @Column (name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column (name = "phone_number", length = 15)
    private String phoneNumber;

    @NotNull
    @Column (name = "email", length = 100, nullable = false)
    private String email;

    @NotNull
    @Column (name = "password", length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Teacher(final TeacherForm form) {
        this.name = form.getName();
        this.lastName = form.getLastName();
        this.phoneNumber = form.getPhoneNumber();
        this.email = form.getEmail();
        this.password = form.getPassword();
        this.role = Role.TEACHER;
    }

    public void updateTeacher(final TeacherForm form) {
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
        if (form.getRole() != null) {
            this.role = form.getRole();
        }
    }

    @PrePersist 
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsDeleted() {
        this.deletedAt = LocalDateTime.now();
    }
}
