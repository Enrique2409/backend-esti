package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.deletedAt IS NULL")
    List<Student> findAllActive();
    Page<Student> findAll(Pageable pageable);

    @Query("SELECT s FROM Student s " +
            "WHERE s.deletedAt IS NULL " +
            "AND (" +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.lastNamePaternal) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.lastNameMaternal) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.curp) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            ")")
    Page<Student> searchStudents(String keyword, Pageable pageable);

    List<Student> findByGroupIdGroup(Long groupId);

    List<Student> findByGroupGroupName(String groupName);

    List<Student> findByGroupGrade(Integer grade);
}