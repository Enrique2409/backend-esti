package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Student;
import org.esti.backend_esti.Entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT t FROM Teacher t WHERE t.deletedAt IS NULL")
    List<Teacher> findAllActive();

    Page<Teacher> findAll(Pageable pageable);
} 