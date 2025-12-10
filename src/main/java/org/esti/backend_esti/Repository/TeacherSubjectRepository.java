package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherSubjectRepository extends JpaRepository<TeacherSubject, Long> {

    @Query("SELECT tsg FROM TeacherSubject tsg WHERE tsg.deletedAt IS NULL")
    List<TeacherSubject> findAllActive();

    @Query("SELECT tsg FROM TeacherSubject tsg WHERE tsg.deletedAt IS NULL AND tsg.teacher.id = :teacherId")
    List<TeacherSubject> findByTeacherId(Long teacherId);

    /*@Query("SELECT tsg FROM TeacherSubject tsg WHERE tsg.deletedAt IS NULL AND tsg.group.id = :groupId")
    List<TeacherSubject> findByGroupId(Long groupId);*/

    @Query("SELECT tsg FROM TeacherSubject tsg WHERE tsg.deletedAt IS NULL AND tsg.subject.id = :subjectId")
    List<TeacherSubject> findBySubjectId(Long subjectId);

    /*@Query("SELECT tsg FROM TeacherSubject tsg WHERE tsg.deletedAt IS NULL AND tsg.teacher.id = :teacherId AND tsg.group.id = :groupId")
    List<TeacherSubject> findByTeacherIdAndGroupId(Long teacherId, Long groupId);*/

    @Query("SELECT tsg FROM TeacherSubject tsg WHERE tsg.deletedAt IS NULL AND tsg.teacher.id = :teacherId AND tsg.subject.id = :subjectId")
    List<TeacherSubject> findByTeacherIdAndSubjectId(Long teacherId, Long subjectId);

    /*@Query("SELECT tsg FROM TeacherSubject tsg WHERE tsg.deletedAt IS NULL AND tsg.group.id = :groupId AND tsg.subject.id = :subjectId")
    TeacherSubject findByGroupIdAndSubjectId(Long groupId, Long subjectId);*/
}
