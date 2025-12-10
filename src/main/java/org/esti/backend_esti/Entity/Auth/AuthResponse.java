package org.esti.backend_esti.Entity.Auth;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esti.backend_esti.Entity.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String id;

    @JsonProperty("role")
    private Role role;

    private String username;
    private String token;

    public String getRoleName() {
        return role != null ? role.name() : null;
    }
}
