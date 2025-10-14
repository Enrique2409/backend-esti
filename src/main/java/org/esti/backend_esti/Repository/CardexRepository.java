package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Admin;
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

    @Query("SELECT c FROM Cardex c " +
            "WHERE c.teacherSubjectGroup.group.id = :groupId " +
            "AND c.deletedAt IS NULL")
    List<Cardex> findByGroupId(Long groupId);

    @Query("SELECT c FROM Cardex c " +
            "WHERE c.student.id = :studentId " +
            "AND c.deletedAt IS NULL")
    List<Cardex> findByStudentId(Long studentId);

    @Query("SELECT c FROM Cardex c " +
            "WHERE c.teacherSubjectGroup.teacher.id = :teacherId " +
            "AND c.deletedAt IS NULL")
    List<Cardex> findByTeacherId(Long teacherId);

    @Query("SELECT c FROM Cardex c " +
            "WHERE c.teacherSubjectGroup.subject.id = :subjectId " +
            "AND c.deletedAt IS NULL")
    List<Cardex> findBySubjectId(Long subjectId);

    Page<Cardex> findAll(Pageable pageable);
    @Query("SELECT c FROM Cardex c " +
            "WHERE c.teacherSubjectGroup.teacher.id = :teacherId " +
            "AND c.teacherSubjectGroup.group.id = :groupId " +
            "AND c.deletedAt IS NULL")
    List<Cardex> findByTeacherAndGroup(Long teacherId, Long groupId);
}
