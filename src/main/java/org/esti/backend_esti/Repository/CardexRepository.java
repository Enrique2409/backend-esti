package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Cardex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardexRepository extends JpaRepository<Cardex, Long> {

    @Query("""
            SELECT c FROM Cardex c
            JOIN c.teacherSubjectGroup tsg
            JOIN tsg.group g
            WHERE g.id = :groupId
            AND c.deletedAt IS NULL
            """)
    List<Cardex> findByGroupId(@Param("groupId") Long groupId);

    @Query("""
            SELECT c FROM Cardex c
            JOIN c.student s
            WHERE s.id = :studentId
            AND c.deletedAt IS NULL
            """)
    List<Cardex> findByStudentId(@Param("studentId") Long studentId);

    @Query("""
            SELECT c FROM Cardex c
            JOIN c.teacherSubjectGroup tsg
            JOIN tsg.teacher t
            WHERE t.id = :teacherId
            AND c.deletedAt IS NULL
            """)
    List<Cardex> findByTeacherId(@Param("teacherId") Long teacherId);

    @Query("""
            SELECT c FROM Cardex c
            JOIN c.teacherSubjectGroup tsg
            JOIN tsg.subject sb
            WHERE sb.id = :subjectId
            AND c.deletedAt IS NULL
            """)
    List<Cardex> findBySubjectId(@Param("subjectId") Long subjectId);

    @Query("""
            SELECT c FROM Cardex c
            JOIN c.teacherSubjectGroup tsg
            JOIN tsg.teacher t
            JOIN tsg.group g
            WHERE t.id = :teacherId
            AND g.id = :groupId
            AND c.deletedAt IS NULL
            """)
    List<Cardex> findByTeacherAndGroup(@Param("teacherId") Long teacherId,
                                       @Param("groupId") Long groupId);

    @Query("""
            SELECT c FROM Cardex c
            JOIN c.student s
            JOIN c.teacherSubjectGroup tsg
            JOIN tsg.teacher t
            WHERE c.deletedAt IS NULL
            AND (
                LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(s.lastNamePaternal) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(s.lastNameMaternal) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(s.curp) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(t.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
            """)
    Page<Cardex> searchCardex(@Param("keyword") String keyword, Pageable pageable);
}
