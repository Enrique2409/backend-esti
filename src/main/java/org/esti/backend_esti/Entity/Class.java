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
@Table(name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_class")
    private Long idClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id", referencedColumnName = "id_level", nullable = false)
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id_group", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_teacher_id", referencedColumnName = "id_subject_teacher", nullable = false)
    private SubjectTeacher subjectTeacher;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    public Class(final ClassForm form) {
        if (form == null) {
            throw new IllegalArgumentException("El formulario de clase no puede ser nulo.");
        }
        this.subjectTeacher = form.getSubjectTeacher();
        this.group = form.getGroup();
        this.level = form.getLevel();
    }

    public void updateClass(final ClassForm form) {
        if (form == null) {
            throw new IllegalArgumentException("El formulario de clase no puede ser nulo.");
        }
        if (form.getSubjectTeacher() != null) { 
            this.subjectTeacher = form.getSubjectTeacher();
        }
        if (form.getGroup() != null) {
            this.group = form.getGroup();
        }
        if (form.getLevel() != null) {
            this.level = form.getLevel();
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
