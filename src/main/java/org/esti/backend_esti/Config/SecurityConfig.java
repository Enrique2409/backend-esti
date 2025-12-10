package org.esti.backend_esti.Config;


import org.esti.backend_esti.Config.Jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(requests -> requests
			.requestMatchers(HttpMethod.POST, "/esti/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/esti/admin/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/esti/admin/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/esti/admin/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/esti/admin/**").permitAll()
                        //.requestMatchers(HttpMethod.POST, "/esti/auth/login").permitAll()

                        .requestMatchers(HttpMethod.GET, "/esti/group/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/esti/group/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/esti/group/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/esti/group/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/esti/teacher/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/esti/teacher/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/esti/teacher/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/esti/teacher/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/esti/student/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/esti/student/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/esti/student/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/esti/student/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/esti/student/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/esti/period/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/esti/period/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/esti/period/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/esti/period/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/esti/cardex/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/esti/cardex/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/esti/cardex/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/esti/cardex/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/esti/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/esti/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/esti/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/esti/**").permitAll()
                        .anyRequest().authenticated()
                )
               // .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();

        config.setAllowedOrigins(java.util.Arrays.asList(
                "http://localhost:3000",
                "https://esti70.org",
                "https://www.esti70.org"));
        config.setAllowedMethods(java.util.Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(java.util.Arrays.asList("*"));
        config.setAllowCredentials(true);

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source =
                new org.springframework.web.cors.UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);
        return source;
    }

}

