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
            JOIN c.student s
            JOIN s.group g
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
            JOIN c.teacherSubject tsg
            JOIN tsg.teacher t
            WHERE t.id = :teacherId
            AND c.deletedAt IS NULL
            """)
    List<Cardex> findByTeacherId(@Param("teacherId") Long teacherId);

    @Query("""
            SELECT c FROM Cardex c
            JOIN c.teacherSubject tsg
            JOIN tsg.subject sb
            WHERE sb.id = :subjectId
            AND c.deletedAt IS NULL
            """)
    List<Cardex> findBySubjectId(@Param("subjectId") Long subjectId);

    /*@Query("""
            SELECT c FROM Cardex c
            JOIN c.teacherSubject tsg
            JOIN tsg.teacher t
            JOIN tsg.group g
            WHERE t.id = :teacherId
            AND g.id = :groupId
            AND c.deletedAt IS NULL
            """)
    List<Cardex> findByTeacherAndGroup(@Param("teacherId") Long teacherId,
                                       @Param("groupId") Long groupId);*/

    @Query("""
            SELECT c FROM Cardex c
            JOIN c.student s
            JOIN c.teacherSubject tsg
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

    @Query("""
        SELECT c FROM Cardex c
        JOIN c.teacherSubject ts
        JOIN ts.teacher t
        JOIN ts.subject s
        JOIN c.student st
        JOIN st.group g
        LEFT JOIN c.period p
        WHERE t.idTeacher = :teacherId
        AND (:subjectName IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :subjectName, '%')))
        AND (:studentName IS NULL OR LOWER(st.name) LIKE LOWER(CONCAT('%', :studentName, '%')))
        AND (:lastNamePaternal IS NULL OR LOWER(st.lastNamePaternal) LIKE LOWER(CONCAT('%', :lastNamePaternal, '%')))
        AND (:lastNameMaternal IS NULL OR LOWER(st.lastNameMaternal) LIKE LOWER(CONCAT('%', :lastNameMaternal, '%')))
        AND (:groupName IS NULL OR LOWER(g.groupName) LIKE LOWER(CONCAT('%', :groupName, '%')))
        AND (:periodId IS NULL OR p.idPeriod = :periodId)
    """)
    List<Cardex> findStudentsByTeacherWithFilters(
            @Param("teacherId") Long teacherId,
            @Param("subjectName") String subjectName,
            @Param("studentName") String studentName,
            @Param("lastNamePaternal") String lastNamePaternal,
            @Param("lastNameMaternal") String lastNameMaternal,
            @Param("groupName") String groupName,
            @Param("periodId") Long periodId
    );


    @Query("""
        SELECT c FROM Cardex c
        JOIN c.teacherSubject ts
        JOIN ts.teacher t
        JOIN ts.subject s
        JOIN c.student st
        JOIN st.group g
        LEFT JOIN c.period p
        WHERE c.deletedAt IS NULL
        AND t.idTeacher = :teacherId
        AND (:subjectName IS NULL OR :subjectName = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', :subjectName, '%')))
        AND (:groupName IS NULL OR :groupName = '' OR LOWER(g.groupName) LIKE LOWER(CONCAT('%', :groupName, '%')))
        AND (:grade IS NULL OR g.grade = :grade)
        AND (
            :keyword IS NULL OR :keyword = '' OR
            LOWER(st.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(st.lastNamePaternal) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(st.lastNameMaternal) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
        ORDER BY g.grade ASC, g.groupName ASC, st.lastNamePaternal ASC, st.lastNameMaternal ASC, st.name ASC
    """)
    Page<Cardex> findByTeacherWithFilters(
            @Param("teacherId") Long teacherId,
            @Param("subjectName") String subjectName,
            @Param("groupName") String groupName,
            @Param("grade") Integer grade,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("""
    SELECT c FROM Cardex c
    JOIN c.teacherSubject ts
    JOIN ts.teacher t
    JOIN ts.subject s
    JOIN c.student st
    JOIN st.group g
    LEFT JOIN c.period p
    WHERE c.deletedAt IS NULL
    AND t.idTeacher = :teacherId
    AND (:subjectId IS NULL OR s.idSubject = :subjectId)
    AND (:groupName IS NULL OR :groupName = '' OR LOWER(g.groupName) LIKE LOWER(CONCAT('%', :groupName, '%')))
    AND (:grade IS NULL OR g.grade = :grade)
    AND (
        :keyword IS NULL OR :keyword = '' OR
        LOWER(st.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(st.lastNamePaternal) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(st.lastNameMaternal) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
    ORDER BY g.grade ASC, g.groupName ASC, st.lastNamePaternal ASC, st.lastNameMaternal ASC, st.name ASC
""")
    Page<Cardex> findByTeacherWithFilters2(
            @Param("teacherId") Long teacherId,
            @Param("subjectId") Long subjectId,
            @Param("groupName") String groupName,
            @Param("grade") Integer grade,
            @Param("keyword") String keyword,
            Pageable pageable
    );

}
