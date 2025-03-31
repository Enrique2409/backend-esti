package org.esti.backend_esti.Service;


import java.util.List;

import org.esti.backend_esti.DTO.AdminDTO;
import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Form.AdminForm;
import org.esti.backend_esti.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return AdminDTO.build(admin);
    }

    public AdminDTO updateAdmin(final AdminForm form, Long id) throws Exception {
        validateIfAdminExists(id);
        final Admin admin = adminRepository.findById(id).get();
        admin.setPassword(passwordEncoder.encode(form.getPassword()));
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

    public List<AdminDTO> getAllAdmins()throws Exception {
        final List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(AdminDTO::build).toList();
    }

    public void validateIfAdminExists(Long id) throws Exception {
        if (!adminRepository.existsById(id)) {
            throw new Exception("Admin not found");
        }
    }
}
