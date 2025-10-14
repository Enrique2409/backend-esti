package org.esti.backend_esti.Service;

import org.esti.backend_esti.Config.Jwt.JwtUtil;
import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Entity.Auth.AuthResponse;
import org.esti.backend_esti.Entity.Role;
import org.esti.backend_esti.Entity.Teacher;
import org.esti.backend_esti.Repository.AdminLoginRepository;
import org.esti.backend_esti.Repository.TeacherLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AdminLoginRepository adminLoginRepository;

    @Autowired
    private TeacherLoginRepository teacherLoginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse login(String email, String password) throws UsernameNotFoundException {
        Admin admin = adminLoginRepository.findByEmail(email);

        if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
            String token = jwtUtil.generateToken(admin.getEmail(), admin.getRole());

            return buildAuthResponse(admin.getIdAdmin().toString(), admin.getName(), token, Role.valueOf(admin.getRole().name()));
        }

        Teacher teacher =teacherLoginRepository.findByEmail(email);

        if (teacher != null && passwordEncoder.matches(password, teacher.getPassword())) {
            String token = jwtUtil.generateToken(teacher.getEmail(), teacher.getRole());

            return buildAuthResponse(teacher.getIdTeacher().toString(), teacher.getName(), token, Role.valueOf(teacher.getRole().name()));
        }
        throw new UsernameNotFoundException("Invalid credentials");
    }


    private AuthResponse buildAuthResponse(String id, String username, String token, Role role) {
        return AuthResponse.builder()
                .id(id)
                .username(username)
                .token(token)
                .role(role)
                .build();
    }
}
