package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query("SELECT a FROM Admin a " +
            "WHERE a.deletedAt IS NULL " +
            "AND (" +
            "LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(a.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            ")")
    Page<Admin> searchAdmins(String keyword, Pageable pageable);
    Optional<Admin> findByEmail(String email);

    @Query(value = "SELECT * FROM administrator WHERE id_admin = :id", nativeQuery = true)
    Admin findAnyById(@Param("id") Long id);
}
