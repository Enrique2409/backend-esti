package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subject_teacher")
public class SubjectTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subject_teacher")
    private Long idSubjectTeacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", referencedColumnName = "id_subject", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id_teacher", nullable = false)
    private Teacher teacher;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    

    public SubjectTeacher(final SubjectTeacherForm form) {
        if (form == null) {
            throw new IllegalArgumentException("El formulario no puede ser nulo");
        }
        this.subject = form.getSubject();
        this.teacher = form.getTeacher();
        this.createdAt = LocalDateTime.now();
    }
    

    public void updateSubjectTeacher(final SubjectTeacherForm form) {
        if (form.getSubject() != null) {
            this.subject = form.getSubject();
        }
        if (form.getTeacher() != null) {
            this.teacher = form.getTeacher();
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
