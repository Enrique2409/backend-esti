package org.esti.backend_esti.Controller;


import org.esti.backend_esti.Entity.Auth.AuthResponse;
import org.esti.backend_esti.Entity.Auth.LoginRequest;
import org.esti.backend_esti.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/esti/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse authResponse = loginService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(authResponse);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return ResponseEntity.ok().body("{\"isValid\": true}");
        } else {
            return ResponseEntity.status(401).body("{\"isValid\": false}");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }
}
