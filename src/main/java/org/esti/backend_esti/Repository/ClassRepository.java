package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    // Aquí puedes agregar métodos personalizados de consulta si los necesitas
} 