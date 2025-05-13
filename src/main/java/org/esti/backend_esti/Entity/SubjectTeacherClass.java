package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.esti.backend_esti.Form.SubjectTeacherForm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subject_teacher_class")
public class SubjectTeacherClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subject_teacher")
    private Long idSubjectTeacherClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_student", referencedColumnName = "id_student")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", referencedColumnName = "id_subject", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id_teacher", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", referencedColumnName = "id_class", nullable = false)
    private CourseClass courseClass;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    

    public SubjectTeacherClass(final SubjectTeacherForm form) {
        if (form == null) {
            throw new IllegalArgumentException("El formulario no puede ser nulo");
        }
        this.subject = form.getSubject();
        this.teacher = form.getTeacher();
        this.courseClass = form.getCourseClass();
        this.createdAt = LocalDateTime.now();
    }

    public void updateSubjectTeacher(final SubjectTeacherForm form) {
        if (form.getSubject() != null) {
            this.subject = form.getSubject();
        }
        if (form.getTeacher() != null) {
            this.teacher = form.getTeacher();
        }
        if (form.getCourseClass() != null) {
            this.courseClass = form.getCourseClass();
        }
        this.updatedAt = LocalDateTime.now();
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
