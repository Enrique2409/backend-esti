package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.SubjectTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectTeacherRepository extends JpaRepository<SubjectTeacher, Long> {

    @Query("SELECT st FROM SubjectTeacher st WHERE st.deletedAt IS NULL")
    List<SubjectTeacher> findAllActive();
}