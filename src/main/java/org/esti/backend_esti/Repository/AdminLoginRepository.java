package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminLoginRepository extends CrudRepository<Admin, String> {
    Admin findByEmail(String email);
}
