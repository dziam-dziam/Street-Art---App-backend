package org.example.services.add_and_register_services;

import org.example.dtos.app_user.AppUserDto;
import org.example.dtos.app_user.RegisterAppUserDto;
import org.example.entities.AppUser;
import org.example.mappers.AppUserMapper;
import org.example.repositories.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterAppUserServiceTest {

    @Mock AppUserMapper appUserMapper;
    @Mock AppUserRepository appUserRepository;
    @Mock PasswordEncoder passwordEncoder;

    @InjectMocks RegisterAppUserService registerAppUserService;

    @Test
    void should_register_user_and_encode_password() {
        RegisterAppUserDto dto = RegisterAppUserDto.builder()
                .appUserName("Ala")
                .appUserEmail("ala@test.com")
                .appUserPassword("secret")
                .appUserNationality("PL")
                .appUserLanguagesSpoken(Set.of("PL", "EN"))
                .appUserCity("Poznań")
                .appUserLiveInDistrict("Jeżyce")
                .build();

        when(appUserRepository.findByAppUserEmail("ala@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("secret")).thenReturn("ENC(secret)");

        AppUser entity = AppUser.builder().id(1L).appUserEmail("ala@test.com").build();
        when(appUserMapper.mapAppUserDtoToAppUserEntity(any(AppUserDto.class))).thenReturn(entity);
        when(appUserRepository.save(entity)).thenReturn(entity);

        AppUserDto expected = AppUserDto.builder().appUserEmail("ala@test.com").appUserPassword("ENC(secret)").build();
        when(appUserMapper.mapAppUserEntityToAppUserDto(entity)).thenReturn(expected);

        AppUserDto result = registerAppUserService.registerAppUser(dto);

        assertNotNull(result);
        assertEquals("ala@test.com", result.getAppUserEmail());
        assertEquals("ENC(secret)", result.getAppUserPassword());

        verify(passwordEncoder).encode("secret");
        verify(appUserRepository).save(entity);
    }

    @Test
    void should_throw_when_email_already_exists() {
        RegisterAppUserDto dto = RegisterAppUserDto.builder()
                .appUserEmail("ala@test.com")
                .appUserPassword("secret")
                .build();

        when(appUserRepository.findByAppUserEmail("ala@test.com")).thenReturn(Optional.of(new AppUser()));

        assertThrows(IllegalArgumentException.class, () -> registerAppUserService.registerAppUser(dto));

        verify(appUserRepository).findByAppUserEmail("ala@test.com");
        verifyNoInteractions(passwordEncoder, appUserMapper);
        verify(appUserRepository, never()).save(any());
    }
}
