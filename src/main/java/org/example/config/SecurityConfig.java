package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        .requestMatchers("/auth/registerCity", "/auth/registerDistrict").permitAll()
                        .requestMatchers("/auth/addCommute").permitAll()
                        .requestMatchers("/map/**").permitAll()
                        .requestMatchers("/api/photos/download/**").permitAll()

                        .requestMatchers("/auth/me").authenticated()
                        .requestMatchers("/auth/logout").authenticated()
                        .requestMatchers("/updateAppUser/me").authenticated()

                        .requestMatchers(HttpMethod.GET, "/getAll/**").hasRole("ADMIN")
                        .requestMatchers("/remove/**").hasRole("ADMIN")
                        .requestMatchers("/updateAppUser/**").hasRole("ADMIN")
                        .requestMatchers("/updateArtPiece/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }
}
