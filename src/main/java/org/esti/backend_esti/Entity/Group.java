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
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Group(Long idGroup) {
        this.idGroup = idGroup;
    }

    public Group(final GroupForm form) {
        this.groupName = form.getGroupName();
    }

    public void updateGroup(final GroupForm form) {
        this.groupName = form.getGroupName();
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
