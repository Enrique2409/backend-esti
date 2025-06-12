package org.esti.backend_esti.Repository;

//import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s WHERE s.deletedAt IS NULL")
    List<Subject> findAllActive();
}