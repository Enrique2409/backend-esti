package org.esti.backend_esti.Service;


import org.esti.backend_esti.DTO.ReportDTO;
import org.esti.backend_esti.Entity.Report;
import org.esti.backend_esti.Form.ReportForm;
import org.esti.backend_esti.Repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public ReportDTO createReport(final ReportForm form) {
        final Report report = new Report(form);
        reportRepository.save(report);
        return ReportDTO.build(report);
    }

    public ReportDTO updateReport(final ReportForm form, Long idReport) throws Exception {
        validateIfReportExists(idReport);
        final Report report = reportRepository.findById(idReport).get();
        report.updateReport(form);
        reportRepository.save(report);
        return ReportDTO.build(report);
    }

    public void deleteReport(final Long idReport) throws Exception {
        validateIfReportExists(idReport);
        reportRepository.deleteById(idReport);
    }

    public ReportDTO findById(Long idReport) throws Exception {
        validateIfReportExists(idReport);
        final  Report report = reportRepository.findById(idReport).orElseThrow(() ->
            new Exception("Report not found with id: " + idReport)
        );
        return ReportDTO.build(report);
    }

    public List<ReportDTO> getAllReports()throws  Exception {
        final  List<Report> reports = reportRepository.findAll();
        return  reports.stream().map(ReportDTO::build).toList();
    }

    public List<Report> getAllActiveReports() {
        return reportRepository.findAllActive();
    }

    public void deleteReportLogically(Long idReport) throws Exception {
        Report existingReport = reportRepository.findById(idReport).orElseThrow(() ->
                new Exception("Report not found with id: " + idReport)
        );
        existingReport.markAsDeleted();
        reportRepository.save(existingReport);
    }

    public void validateIfReportExists(Long idReport) throws Exception {
        if (!reportRepository.existsById(idReport)) {
            throw new Exception("Report Not Found");
        }
    }
}
