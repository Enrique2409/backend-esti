package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.esti.backend_esti.Form.TeacherSubjectGroupForm;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teacher_subject_group")
public class TeacherSubjectGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_teacher_subject_group")
    private Long idTeacherSubjectGroup;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_group", nullable = false)
    private Group group;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_teacher", nullable = false)
    private Teacher teacher;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_subject", nullable = false)
    private Subject subject;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public TeacherSubjectGroup(final TeacherSubjectGroupForm form, final Group group, final Teacher teacher, final Subject subject) {
        this.group = group;
        this.teacher = teacher;
        this.subject = subject;
    }

    public void updateFromForm(final TeacherSubjectGroupForm form, final Group group, final Teacher teacher, final Subject subject) {
        this.group = group;
        this.teacher = teacher;
        this.subject = subject;
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
