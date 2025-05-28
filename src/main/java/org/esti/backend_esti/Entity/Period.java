package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.esti.backend_esti.Form.PeriodForm;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "period")
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_period")
    private Long idPeriod;

    @NotNull
    @Size(max = 20)
    @Column(name = "cve", nullable = false, unique = true)
    private String cve;

    @NotNull
    @Size(max = 100)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Period(Long idPeriod) {
        this.idPeriod = idPeriod;
    }

    public Period(final PeriodForm form) {
        this.cve = form.getCve();
        this.description = form.getDescription();
    }

    public void updatePeriod(final PeriodForm form) {
        this.cve = form.getCve();
        this.description = form.getDescription();
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
