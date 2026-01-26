package org.example.services.config_services;

import lombok.RequiredArgsConstructor;
import org.example.entities.AppUser;
import org.example.exceptions.AppUserNotFoundByEmailException;
import org.example.repositories.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    //TODO finals
    @Override
    public UserDetails loadUserByUsername(String email) {
        AppUser user = appUserRepository.findByAppUserEmail(email)
                .orElseThrow(() -> new AppUserNotFoundByEmailException(email));

        boolean isAdmin = user.getAppUserEmail().equalsIgnoreCase("damianzmudzinski3@gmail.com");

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getAppUserEmail())
                .password(user.getAppUserPassword())
                .roles(isAdmin ? "ADMIN" : "USER")
                .build();
    }

}
