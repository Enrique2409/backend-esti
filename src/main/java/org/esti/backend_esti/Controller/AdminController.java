package org.esti.backend_esti.Controller;

import java.util.List;

import org.esti.backend_esti.DTO.AdminDTO;
import org.esti.backend_esti.Form.AdminForm;
import org.esti.backend_esti.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/esti/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/")
    public ResponseEntity createAdmin(@RequestBody @Valid AdminForm form) {
        AdminDTO adminDTO = adminService.createAdmin(form);
        return ResponseEntity.ok().body(adminDTO);
    }

    @PatchMapping("/{adminId}")
    public ResponseEntity updateAdmin (@RequestBody @Valid AdminForm form, @PathVariable("adminId") final Long adminId) throws Exception {
        AdminDTO adminDTO = adminService.updateAdmin(form, adminId);
        return ResponseEntity.ok().body(adminDTO);
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity deleteAdmin(@PathVariable("adminId") final Long adminId) throws Exception {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{adminId}")
    public ResponseEntity findById(@PathVariable("adminId") final Long adminId) throws Exception {
        AdminDTO adminDTO = adminService.findById(adminId);
        return ResponseEntity.ok().body(adminId);
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllAdmins() throws Exception {
        List<AdminDTO> adminsDTO = adminService.getAllAdmins();
        return ResponseEntity.ok().body(adminsDTO);
    }
}
