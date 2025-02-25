package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Métodos para buscar estudiantes por clase o email
} 