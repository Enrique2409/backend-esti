package org.esti.backend_esti.Service;


import org.esti.backend_esti.Config.Jwt.JwtUtil;
import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Entity.Auth.AuthResponse;
import org.esti.backend_esti.Repository.AdminLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private AdminLoginRepository adminLoginRepository;

    /*@Autowired
    private CustomerLoginRepository customerLoginRepository;

    @Autowired
    private SupervisorLoginRepository supervisorLoginRepository;*/

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminLoginRepository.findByEmail(username);

        if (admin != null) {
            return createUserDetails(admin.getIdAdmin().toString(), admin.getPassword());
        }

        /*Customer customer = customerLoginRepository.findByEmail(username);

        if (customer != null) {
            return createUserDetails(customer.getId().toString(), customer.getPassword(), customer.getRole().name());
        }

        Supervisor supervisor = supervisorLoginRepository.findByEmail(username);

        if (supervisor != null) {
            return createUserDetails(supervisor.getId().toString(), supervisor.getPassword(), supervisor.getRole().name());
        }*/
        throw new UsernameNotFoundException("User not found");
    }

    private UserDetails createUserDetails(String id, String password) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(id)
                .password(password)
                .build();
    }

    public AuthResponse login(String email, String password) throws UsernameNotFoundException {
        Admin admin = adminLoginRepository.findByEmail(email);

        if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
            String token = jwtUtil.generateToken(admin.getEmail());
            return buildAuthResponse(admin.getIdAdmin().toString(), admin.getName(), token);
        }

        /*Customer customer = customerLoginRepository.findByEmail(email);

        if (customer != null && passwordEncoder.matches(password, customer.getPassword())) {
            String token = jwtUtil.generateToken(customer.getEmail(), customer.getRole().name());
            return buildAuthResponse(customer.getId().toString(), customer.getRole(), customer.getFirstName(), token);
        }

        Supervisor supervisor = supervisorLoginRepository.findByEmail(email);

        if (supervisor != null && passwordEncoder.matches(password, supervisor.getPassword())) {
            String token = jwtUtil.generateToken(supervisor.getEmail(), supervisor.getRole().name());
            return buildAuthResponse(supervisor.getId().toString(), supervisor.getRole(), supervisor.getFirstName(), token);
        }*/

        throw new UsernameNotFoundException("Invalid credentials");
    }

    private AuthResponse buildAuthResponse(String id, String username, String token) {
        return AuthResponse.builder()
                .id(id)
                .username(username)
                .token(token)
                .build();
    }
}
