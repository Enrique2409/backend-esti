package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.esti.backend_esti.Form.ClassForm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "classes")
public class CourseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_class")
    private Long idClass;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "level_id", referencedColumnName = "id_level", nullable = false)
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id", referencedColumnName = "id_group", nullable = false)
    private Group group;

    @OneToMany(mappedBy = "courseClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "courseClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<>();

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    public CourseClass(final ClassForm form) {
        if (form == null) {
            throw new IllegalArgumentException("El formulario de clase no puede ser nulo.");
        }
        this.group = form.getGroup();
        this.level = form.getLevel();
        if (form.getStudent() != null) {
            this.students.add(form.getStudent());
        }

        if (form.getSubject() != null) {
            this.subjects.add(form.getSubject());
        }
    }

    public void updateClass(final ClassForm form) {
        if (form == null) {
            throw new IllegalArgumentException("El formulario de clase no puede ser nulo.");
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
