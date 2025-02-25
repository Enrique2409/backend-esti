package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.SubjectTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectTeacherRepository extends JpaRepository<SubjectTeacher, Long> {
    // Métodos para buscar asignaciones por profesor o materia
} 