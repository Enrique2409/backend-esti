package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // Métodos para buscar profesores por especialización o email
} 