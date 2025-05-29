package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Cardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardexRepository extends JpaRepository<Cardex, Long> {

    @Query("SELECT c FROM Cardex c WHERE c.deletedAt IS NULL")
    List<Cardex> findAllActive();

    // Filtrar por grupo
    @Query("SELECT c FROM Cardex c WHERE c.group.id = :groupId AND c.deletedAt IS NULL")
    List<Cardex> findByGroupId(Long groupId);

    // Filtrar por alumno
    @Query("SELECT c FROM Cardex c WHERE c.student.id = :studentId AND c.deletedAt IS NULL")
    List<Cardex> findByStudentId(Long studentId);

    // Filtrar por maestro
    @Query("SELECT c FROM Cardex c WHERE c.teacher.id = :teacherId AND c.deletedAt IS NULL")
    List<Cardex> findByTeacherId(Long teacherId);

    // Filtrar por materia
    @Query("SELECT c FROM Cardex c WHERE c.subject.id = :subjectId AND c.deletedAt IS NULL")
    List<Cardex> findBySubjectId(Long subjectId);
}
