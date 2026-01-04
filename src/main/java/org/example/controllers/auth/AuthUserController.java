package org.example.controllers.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.dtos.user.LoginAppUserDto;
import org.example.dtos.user.RegisterAppUserDto;
import org.example.entities.AppUser;
import org.example.exceptions.AppUserNotFoundException;
import org.example.repositories.AppUserRepository;
import org.example.services.add_and_register_services.RegisterAppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthUserController {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RegisterAppUserService registerAppUserService;

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterAppUserDto registerAppUserDto) {
        if (appUserRepository.findByAppUserEmail(registerAppUserDto.getAppUserEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Email already exists");
        }

        registerAppUserService.registerAppUser(registerAppUserDto);

        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginAppUserDto loginAppUserDto,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {

        String loginAppUserDtoEmail = loginAppUserDto.getAppUserEmail();
        String loginAppUserDtoPassword = loginAppUserDto.getAppUserPassword();

        AppUser appUser = appUserRepository.findByAppUserEmail(loginAppUserDtoEmail)
                .orElseThrow(() -> new AppUserNotFoundException(loginAppUserDtoEmail));

        if (!passwordEncoder.matches(loginAppUserDtoPassword, appUser.getAppUserPassword())) {
            throw new RuntimeException("Bad credentials");
        }

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginAppUserDtoEmail, loginAppUserDtoPassword)
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(context, request, response);
        return ResponseEntity.ok("Logged in");
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, auth);
        return ResponseEntity.ok("Logged out");
    }
}

