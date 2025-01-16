package org.esti.backend_esti.DTO;

import lombok.Builder;
import lombok.Data;
import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Entity.Report;
import org.esti.backend_esti.Entity.ReportStatus;

import java.time.LocalDate;

@Data
@Builder
public class ReportDTO {

    private Long idReport;

    private String reportTitle;

    private LocalDate monthStart;

    private LocalDate monthEnd;

    private String academicYear;

    private Admin createdBy;

    private String notes;

    private ReportStatus status = ReportStatus.EN_PROGRESO;

    public static ReportDTO build(final Report report) {
        return ReportDTO.builder()
                .idReport(report.getIdReport())
                .reportTitle(report.getReportTitle())
                .monthStart(report.getMonthStart())
                .monthEnd(report.getMonthEnd())
                .academicYear(report.getAcademicYear())
                .createdBy(report.getCreatedBy())
                .notes(report.getNotes())
                .status(report.getStatus())
                .build();
    }
}
