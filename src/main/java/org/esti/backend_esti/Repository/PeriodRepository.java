package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {
    @Query("SELECT g FROM Period g WHERE g.deletedAt IS NULL")
    List<Period> findAllActive();
}
