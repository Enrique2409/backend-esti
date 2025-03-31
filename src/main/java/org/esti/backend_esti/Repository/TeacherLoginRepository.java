package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherLoginRepository extends CrudRepository<Teacher, String> {
    Teacher findByEmail(String email);
}
