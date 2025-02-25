package org.esti.backend_esti.Form;


import java.io.Serializable;
import java.time.LocalDate;

import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Entity.ReportStatus;

import lombok.Data;

@Data
public class ReportForm implements Serializable {

    private String reportTitle;

    private LocalDate monthStart;

    private LocalDate monthEnd;

    private String academicYear;

    private Admin createdBy;

    private String notes;

    private ReportStatus status = ReportStatus.EN_PROGRESO;
}
