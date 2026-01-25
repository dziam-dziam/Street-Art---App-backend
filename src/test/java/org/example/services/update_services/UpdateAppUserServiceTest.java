package org.example.services.update_services;

import org.example.dtos.app_user.AppUserDto;
import org.example.dtos.app_user.UpdateAppUserDto;
import org.example.dtos.commute.CommuteDto;
import org.example.entities.AppUser;
import org.example.entities.City;
import org.example.entities.Commute;
import org.example.entities.District;
import org.example.enums.MeansOfTransport;
import org.example.mappers.AppUserMapper;
import org.example.mappers.CommuteMapper;
import org.example.repositories.AppUserRepository;
import org.example.repositories.CityRepository;
import org.example.repositories.DistrictRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateAppUserServiceTest {

    //TODO private pola!

    @Mock AppUserRepository appUserRepository;
    @Mock CityRepository cityRepository;
    @Mock CommuteMapper commuteMapper;
    @Mock DistrictRepository districtRepository;
    @Mock AppUserMapper appUserMapper;

    @InjectMocks UpdateAppUserService updateAppUserService;

    //TODO dodaj case jezeli sie nie uda

    @Test
    void should_update_user_and_replace_commutes() {
        AppUser user = AppUser.builder()
                .id(1L)
                .appUserEmail("old@test.com")
                .appUserName("Old")
                .appUserPassword("oldpass")
                .build();

        Commute oldCommute = Commute.builder().id(1L).build();
        user.addCommute(oldCommute);
        assertEquals(1, user.getAppUserCommutes().size());

        when(appUserRepository.findByAppUserEmail("old@test.com")).thenReturn(Optional.of(user));

        City city = City.builder().id(10L).cityName("Poznań").build();
        District district = District.builder().id(20L).districtName("Jeżyce").build();
        when(cityRepository.findByCityName("Poznań")).thenReturn(Optional.of(city));
        when(districtRepository.findByDistrictName("Jeżyce")).thenReturn(Optional.of(district));

        CommuteDto newCommuteDto = CommuteDto.builder()
                .commuteThroughDistrictName("Jeżyce")
                .commuteTripsPerWeek(3)
                .commuteStartHour(8)
                .commuteStopHour(17)
                .commuteMeansOfTransport(Set.of(MeansOfTransport.TRAM))
                .commutingAppUserEmail("new@test.com")
                .build();

        Commute newCommuteEntity = Commute.builder().id(2L).build();
        when(commuteMapper.mapCommuteDtoToCommuteEntities(newCommuteDto, user)).thenReturn(newCommuteEntity);

        UpdateAppUserDto updateDto = UpdateAppUserDto.builder()
                .appUserName("NewName")
                .appUserEmail("new@test.com")
                .appUserPassword("newpass")
                .appUserCity("Poznań")
                .appUserLiveInDistrict("Jeżyce")
                .appUserLanguagesSpoken(Set.of("EN"))
                .appUserCommuteDtos(Set.of(newCommuteDto))
                .build();

        when(appUserMapper.mapAppUserEntityToAppUserDto(user))
                .thenReturn(AppUserDto.builder().appUserEmail("new@test.com").appUserName("NewName").build());

        AppUserDto result = updateAppUserService.updateAppUserByEmail(updateDto, "old@test.com");

        assertEquals("NewName", user.getAppUserName());
        assertEquals("new@test.com", user.getAppUserEmail());
        assertEquals("newpass", user.getAppUserPassword());
        assertSame(city, user.getAppUserCity());
        assertSame(district, user.getAppUserLiveInDistrict());

        assertEquals(1, user.getAppUserCommutes().size());
        Commute only = user.getAppUserCommutes().iterator().next();
        assertEquals(2L, only.getId());
        assertSame(user, only.getCommutingAppUser());

        assertEquals("new@test.com", result.getAppUserEmail());

        verify(appUserRepository).save(user);
    }
}
