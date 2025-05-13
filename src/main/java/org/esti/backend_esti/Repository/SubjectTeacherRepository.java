package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.SubjectTeacherClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectTeacherRepository extends JpaRepository<SubjectTeacherClass, Long> {

    @Query("SELECT st FROM SubjectTeacherClass st WHERE st.deletedAt IS NULL")
    List<SubjectTeacherClass> findAllActive();
}