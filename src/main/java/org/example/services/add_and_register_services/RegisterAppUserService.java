package org.example.services.add_and_register_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.user.AppUserDto;
import org.example.dtos.user.RegisterAppUserDto;
import org.example.entities.AppUser;
import org.example.mappers.AppUserMapper;
import org.example.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class RegisterAppUserService {
    private final AppUserMapper appUserMapper;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserDto registerAppUser(RegisterAppUserDto registerAppUserDto) {
        if (registerAppUserDto == null) throw new IllegalArgumentException("RegisterAppUserDto is null");

        String enteredEmail = registerAppUserDto.getAppUserEmail();
        if (appUserRepository.findByAppUserEmail(enteredEmail).isPresent()) {
            throw new IllegalArgumentException("User with email: " + enteredEmail + " already exists");
        }

        String registerAppUserDtoPassword = registerAppUserDto.getAppUserPassword();
        String appUserDtoEncodedPassword = passwordEncoder.encode(registerAppUserDtoPassword);

        AppUserDto appUserDto = AppUserDto.builder()
                .appUserCity(registerAppUserDto.getAppUserCity())
                .appUserName(registerAppUserDto.getAppUserName())
                .appUserEmail(registerAppUserDto.getAppUserEmail())
                .appUserPassword(appUserDtoEncodedPassword)
                .appUserLanguagesSpoken(registerAppUserDto.getAppUserLanguagesSpoken())
                .appUserNationality(registerAppUserDto.getAppUserNationality())
                .appUserLiveInDistrict(registerAppUserDto.getAppUserLiveInDistrict())
                .appUserCommuteDtos(new HashSet<>())
                .build();

        AppUser appUser = appUserMapper.mapAppUserDtoToAppUserEntity(appUserDto);
        AppUser saved = appUserRepository.save(appUser);

        return appUserMapper.mapAppUserEntityToAppUserDto(saved);
    }
}
