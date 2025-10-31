package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Student;
import org.esti.backend_esti.Entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s " +
            "WHERE s.deletedAt IS NULL " +
            "AND (" +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.lastNamePaternal) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.lastNameMaternal) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.curp) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            ")")
    Page<Student> searchStudents(String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM students WHERE id_student = :id", nativeQuery = true)
    Student findAnyById(@Param("id") Long id);

    @Query("SELECT s FROM Student s WHERE s.deletedAt IS NULL AND s.group.id = :groupId")
    List<Student> findByGroupId(Long groupId);
}