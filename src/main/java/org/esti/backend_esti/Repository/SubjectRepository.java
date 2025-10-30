package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s " +
            "WHERE s.deletedAt IS NULL " +
            "AND LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Subject> searchSubjects(String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM subject WHERE id_subject = :id", nativeQuery = true)
    Subject findAnyById(@Param("id") Long id);


} 