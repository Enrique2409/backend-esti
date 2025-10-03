package org.esti.backend_esti.Service;


import java.time.LocalDateTime;
import java.util.List;

import org.esti.backend_esti.DTO.AdminDTO;
import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Form.AdminForm;
import org.esti.backend_esti.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminDTO createAdmin(final AdminForm form){
        final Admin admin = new Admin(form);
        admin.setPassword(passwordEncoder.encode(form.getPassword()));
        adminRepository.save(admin);
        admin.setCreatedAt(LocalDateTime.now());
        return AdminDTO.build(admin);
    }

    public AdminDTO updateAdmin(final AdminForm form, Long id) throws Exception {
        validateIfAdminExists(id);
        final Admin admin = adminRepository.findById(id).get();

        if (form.getPassword() != null && !form.getPassword().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(form.getPassword());
            admin.setPassword(encryptedPassword);
        }
        admin.updateAdmin(form);
        adminRepository.save(admin);
        return AdminDTO.build(admin);
    }

    public void deleteAdmin(final Long id) throws Exception {
        validateIfAdminExists(id);
        adminRepository.deleteById(id);
    }

    public AdminDTO findById(Long id) throws Exception {
        validateIfAdminExists(id);
        final Admin admin = adminRepository.findById(id).get();
        return AdminDTO.build(admin);
    }

    /*
    public List<AdminDTO> getAllAdmins()throws Exception {
        final List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(AdminDTO::build).toList();
    }*/

    public Page<AdminDTO> getAdmins(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Admin> adminsPage = adminRepository.findAll(pageable);
        return adminsPage.map(AdminDTO::build);
    }

    public Page<AdminDTO> searchAdmins(String keyword, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Admin> adminsPage = adminRepository.searchAdmins(keyword, pageable);
        return adminsPage.map(AdminDTO::build);
    }



    public void validateIfAdminExists(Long id) throws Exception {
        if (!adminRepository.existsById(id)) {
            throw new Exception("Admin not found");
        }
    }
}
