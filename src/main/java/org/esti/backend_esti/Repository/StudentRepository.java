package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.deletedAt IS NULL")
    List<Student> findAllActive();
}