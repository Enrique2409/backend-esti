package org.esti.backend_esti.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"month_start", "month_end"})
})
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_report")
    private Long idReport;

    @Column(name = "report_title", nullable = false)
    private String reportTitle;

    @Column(name = "month_start", nullable = false)
    private LocalDate monthStart;

    @Column(name = "month_end", nullable = false)
    private LocalDate monthEnd;

    @Column(name = "academic_year", length = 20)
    private String academicYear;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id_admin")
    private Admin createdBy;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "status", nullable = false, length = 50)
    private ReportStatus status = ReportStatus.EN_PROGRESO;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

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
