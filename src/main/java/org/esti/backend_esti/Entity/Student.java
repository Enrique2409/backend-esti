package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.esti.backend_esti.Form.StudentForm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students", uniqueConstraints = {@UniqueConstraint(columnNames = {"curp"})})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student")
    private Long idStudent;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "last_name_paternal", length = 100, nullable = false)
    private String lastNamePaternal;

    @Column(name = "last_name_maternal", length = 100, nullable = false)
    private String lastNameMaternal;

    @NotNull
    @Pattern(regexp = "^[A-Z0-9]{18}$", message = "CURP inválida")
    @Column(name = "curp", length = 18, nullable = false, unique = true)
    private String curp;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "class_id", referencedColumnName = "id_class", nullable = false)
    private CourseClass courseClass;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubjectTeacherClass> subjectTeacherClasses;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Student(final StudentForm form) {
        if (form == null) {
            throw new IllegalArgumentException("El formulario de estudiante no puede ser nulo.");
        }
        this.name = form.getName();
        this.lastNamePaternal = form.getLastNamePaternal();
        this.lastNameMaternal = form.getLastNameMaternal();
        this.curp = form.getCurp();
        this.birthDate = form.getBirthDate();
        this.phoneNumber = form.getPhoneNumber();
        this.courseClass = form.getCourseClass();
    }

    public void updateStudent(final StudentForm form) {
        if (form == null) {
            throw new IllegalArgumentException("El formulario de estudiante no puede ser nulo.");
        }
        if (form.getName() != null) {
            this.name = form.getName();
        }
        if (form.getLastNamePaternal() != null) {
            this.lastNamePaternal = form.getLastNamePaternal();
        }
        if (form.getLastNameMaternal() != null) {
            this.lastNameMaternal = form.getLastNameMaternal();
        }
        if (form.getCurp() != null) {
            this.curp = form.getCurp();
        }
        if (form.getBirthDate() != null) {
            this.birthDate = form.getBirthDate();
        }
        if (form.getPhoneNumber() != null) {
            this.phoneNumber = form.getPhoneNumber();
        }
        if (form.getCourseClass() != null) {
            this.courseClass = form.getCourseClass();
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
