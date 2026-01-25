package org.example.services.get_services.get_all_services;

import org.example.dtos.app_user.AppUserAdminDto;
import org.example.entities.AppUser;
import org.example.mappers.AppUserMapper;
import org.example.repositories.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllAppUsersServiceTest {

    @Mock AppUserRepository appUserRepository;
    @Mock AppUserMapper appUserMapper;

    @InjectMocks GetAllAppUsersService getAllAppUsersService;

    @Test
    void should_return_list_of_appUserDtos_when_users_exist() {
        AppUser u1 = AppUser.builder().appUserEmail("a@a.com").build();
        AppUser u2 = AppUser.builder().appUserEmail("b@b.com").build();

        when(appUserRepository.findAll()).thenReturn(List.of(u1, u2));

        when(appUserMapper.mapAppUserEntityToAdminDto(u1))
                .thenReturn(AppUserAdminDto.builder().appUserEmail("a@a.com").build());
        when(appUserMapper.mapAppUserEntityToAdminDto(u2))
                .thenReturn(AppUserAdminDto.builder().appUserEmail("b@b.com").build());

        List<AppUserAdminDto> result = getAllAppUsersService.getAllAppUsers();

        assertEquals(2, result.size());
        assertEquals("a@a.com", result.get(0).getAppUserEmail());
        assertEquals("b@b.com", result.get(1).getAppUserEmail());

        verify(appUserRepository).findAll();
        verify(appUserMapper).mapAppUserEntityToAdminDto(u1);
        verify(appUserMapper).mapAppUserEntityToAdminDto(u2);
    }

    @Test
    void should_return_empty_list_when_no_users() {
        when(appUserRepository.findAll()).thenReturn(List.of());

        List<AppUserAdminDto> result = getAllAppUsersService.getAllAppUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(appUserRepository).findAll();
        verifyNoInteractions(appUserMapper);
    }
}
