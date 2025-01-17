package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM Report r " +
            "WHERE (r.monthStart <= :endDate AND r.monthEnd >= :startDate)")
    Optional<Report> findOverlappingReports(LocalDate startDate, LocalDate endDate);

    @Query("SELECT r FROM Report r WHERE r.deletedAt IS NULL")
    List<Report> findAllActive();
}
