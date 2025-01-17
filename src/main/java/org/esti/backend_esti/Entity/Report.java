package org.esti.backend_esti.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esti.backend_esti.Form.ReportForm;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ReportStatus status = ReportStatus.EN_PROGRESO;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Report(final ReportForm form) {
        this.reportTitle = form.getReportTitle();
        this.monthStart = form.getMonthStart();
        this.monthEnd = form.getMonthEnd();
        this.academicYear = form.getAcademicYear();
        this.createdBy = form.getCreatedBy();
        this.notes = form.getNotes();
        this.status = form.getStatus();
    }

    public void updateReport(final ReportForm form) {
        this.reportTitle = form.getReportTitle();
        this.monthStart = form.getMonthStart();
        this.monthEnd = form.getMonthEnd();
        this.academicYear = form.getAcademicYear();
        this.createdBy = form.getCreatedBy();
        this.notes = form.getNotes();
        this.status = form.getStatus();
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
