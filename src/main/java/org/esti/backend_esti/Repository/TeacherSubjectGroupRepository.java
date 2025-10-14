package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.TeacherSubjectGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherSubjectGroupRepository extends JpaRepository<TeacherSubjectGroup, Long> {

    @Query("SELECT tsg FROM TeacherSubjectGroup tsg WHERE tsg.deletedAt IS NULL")
    List<TeacherSubjectGroup> findAllActive();

    @Query("SELECT tsg FROM TeacherSubjectGroup tsg WHERE tsg.deletedAt IS NULL AND tsg.teacher.id = :teacherId")
    List<TeacherSubjectGroup> findByTeacherId(Long teacherId);

    @Query("SELECT tsg FROM TeacherSubjectGroup tsg WHERE tsg.deletedAt IS NULL AND tsg.group.id = :groupId")
    List<TeacherSubjectGroup> findByGroupId(Long groupId);

    @Query("SELECT tsg FROM TeacherSubjectGroup tsg WHERE tsg.deletedAt IS NULL AND tsg.subject.id = :subjectId")
    List<TeacherSubjectGroup> findBySubjectId(Long subjectId);

    @Query("SELECT tsg FROM TeacherSubjectGroup tsg WHERE tsg.deletedAt IS NULL AND tsg.teacher.id = :teacherId AND tsg.group.id = :groupId")
    List<TeacherSubjectGroup> findByTeacherIdAndGroupId(Long teacherId, Long groupId);

    @Query("SELECT tsg FROM TeacherSubjectGroup tsg WHERE tsg.deletedAt IS NULL AND tsg.teacher.id = :teacherId AND tsg.subject.id = :subjectId")
    List<TeacherSubjectGroup> findByTeacherIdAndSubjectId(Long teacherId, Long subjectId);

    @Query("SELECT tsg FROM TeacherSubjectGroup tsg WHERE tsg.deletedAt IS NULL AND tsg.group.id = :groupId AND tsg.subject.id = :subjectId")
    TeacherSubjectGroup findByGroupIdAndSubjectId(Long groupId, Long subjectId);
}
