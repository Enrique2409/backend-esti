package org.esti.backend_esti.Form;


import lombok.Data;
import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Entity.ReportStatus;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ReportForm implements Serializable {

    private String reportTitle;

    private LocalDate monthStart;

    private LocalDate monthEnd;

    private String academicYear;

    private Long createdBy;

    private String notes;

    private ReportStatus status = ReportStatus.EN_PROGRESO;
}
