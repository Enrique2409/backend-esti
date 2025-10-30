package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Subject;
import org.esti.backend_esti.Entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT t FROM Teacher t " +
            "WHERE t.deletedAt IS NULL " +
            "AND (" +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            ")")
    Page<Teacher> searchTeachers(String keyword, Pageable pageable);
    Optional<Teacher> findByEmail(String email);

    @Query(value = "SELECT * FROM teacher WHERE id_teacher = :id", nativeQuery = true)
    Teacher findAnyById(@Param("id") Long id);

} 