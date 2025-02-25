package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    // Métodos personalizados para búsqueda de materias
} 