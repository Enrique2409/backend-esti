package org.esti.backend_esti.Controller;


import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.ReportDTO;
import org.esti.backend_esti.Entity.Report;
import org.esti.backend_esti.Form.ReportForm;
import org.esti.backend_esti.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esti/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/create-report")
    public ResponseEntity createReport(@RequestBody @Valid ReportForm form) {
        ReportDTO reportDTO = reportService.createReport(form);
        return ResponseEntity.ok().body(reportDTO);
    }

    @PatchMapping("/{reportId}")
    public ResponseEntity createReport(@RequestBody @Valid ReportForm form, @PathVariable("reportId") final Long reportId) throws Exception {
        ReportDTO reportDTO = reportService.updateReport(form, reportId);
        return ResponseEntity.ok().body(reportId);
    }

    @DeleteMapping("/{reportId}/hard")
    public ResponseEntity deleteReport(@PathVariable("reportId") final Long reportId) throws Exception {
        reportService.deleteReport(reportId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReportLogically(@PathVariable Long reportId) throws Exception {
        reportService.deleteReportLogically(reportId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{reportId}")
    public ResponseEntity findById(@PathVariable("reportId") final Long reportId) throws Exception {
        ReportDTO reportDTO = reportService.findById(reportId);
        return ResponseEntity.ok().body(reportId);
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllReports() throws Exception {
        List<ReportDTO> reportsDTO = reportService.getAllReports();
        return ResponseEntity.ok().body(reportsDTO);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ReportDTO>> getAllActiveReports() {
        List<Report> activeReports = reportService.getAllActiveReports();
        List<ReportDTO> activeReportDTOs = activeReports.stream().map(ReportDTO::build).toList();
        return ResponseEntity.ok(activeReportDTOs);
    }
}
