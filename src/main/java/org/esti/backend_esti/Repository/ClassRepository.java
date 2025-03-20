package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.CourseClass;
import org.esti.backend_esti.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<CourseClass, Long> {

    @Query("SELECT c FROM CourseClass c WHERE c.deletedAt IS NULL")
    List<CourseClass> findAllActive();
}
