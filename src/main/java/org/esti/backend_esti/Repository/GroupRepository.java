package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("SELECT g FROM Group g WHERE g.deletedAt IS NULL")
    List<Group> findAllActive();
}
