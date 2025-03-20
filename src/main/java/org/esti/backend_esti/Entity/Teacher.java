package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.esti.backend_esti.Form.TeacherForm;

import java.time.LocalDateTime;

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

    @Column (name = "email", length = 100, nullable = false)
    private String email;

    @Column (name = "password", length = 255, nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id", referencedColumnName = "id_level", nullable = false)
    private Level level;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id_group", nullable = false)
    private Group group;

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
