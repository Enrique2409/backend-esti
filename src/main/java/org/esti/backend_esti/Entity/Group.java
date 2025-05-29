package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.esti.backend_esti.Form.GroupForm;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_group")
    private Long idGroup;

    @NotNull
    @Size(max = 50)
    @Column(name = "group_name", nullable = false)
    private String groupName;

    @NotNull
    @Column(name = "grade", nullable = false)
    private Integer grade;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_period", nullable = false)
    private Period period;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Group(Long idGroup) {
        this.idGroup = idGroup;
    }

    public Group(final GroupForm form, Period period) {
        this.groupName = form.getGroupName();
        this.grade = form.getGrade();
        this.period = period;
    }

    public void updateGroup(final GroupForm form, final Period period) {
        this.groupName = form.getGroupName();
        this.grade = form.getGrade();
        this.period = period;
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
