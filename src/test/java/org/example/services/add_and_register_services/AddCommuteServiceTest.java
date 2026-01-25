package org.example.services.add_and_register_services;

import org.example.dtos.commute.AddCommuteDto;
import org.example.dtos.commute.CommuteDto;
import org.example.entities.AppUser;
import org.example.entities.Commute;
import org.example.enums.MeansOfTransport;
import org.example.exceptions.AppUserNotFoundByEmailException;
import org.example.mappers.CommuteMapper;
import org.example.repositories.AppUserRepository;
import org.example.repositories.CommuteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddCommuteServiceTest {

    //TODO private pola!

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    CommuteMapper commuteMapper;

    @Mock
    CommuteRepository commuteRepository;

    @InjectMocks
    AddCommuteService addCommuteService;

    @Test
    void should_add_commute() {
        String commutingAppUserEmail = "marek@gmail.com";

        long commutingAppUserId = 1L;
        int commuteStartHour = 8;
        int commuteStopHour = 19;
        int commuteTripsPerWeek = 20;

        MeansOfTransport tram = MeansOfTransport.TRAM;
        MeansOfTransport bike = MeansOfTransport.BIKE;
        HashSet<MeansOfTransport> meansOfTransports = new HashSet<>();
        meansOfTransports.add(tram);
        meansOfTransports.add(bike);

        AppUser appUserWithCommute = AppUser.builder()
                .id(commutingAppUserId)
                .appUserEmail(commutingAppUserEmail)
                .appUserCommutes(new HashSet<>())
                .build();

        AddCommuteDto addCommuteDto = AddCommuteDto.builder()
                .commuteMeansOfTransport(meansOfTransports)
                .commuteStartHour(commuteStartHour)
                .commuteStopHour(commuteStopHour)
                .commuteTripsPerWeek(commuteTripsPerWeek)
                .build();

        CommuteDto commuteDto = CommuteDto.builder()
                .commuteMeansOfTransport(meansOfTransports)
                .commuteStartHour(commuteStartHour)
                .commuteStopHour(commuteStopHour)
                .commuteTripsPerWeek(commuteTripsPerWeek)
                .commutingAppUserEmail(commutingAppUserEmail)
                .build();

        Commute commuteEntity = Commute.builder()
                .commutingAppUser(appUserWithCommute)
                .commuteMeansOfTransport(meansOfTransports)
                .commuteStopHour(commuteStopHour)
                .commuteStartHour(commuteStartHour)
                .commuteTripsPerWeek(commuteTripsPerWeek)
                .build();

        when(appUserRepository.findByAppUserEmail(commutingAppUserEmail))
                .thenReturn(Optional.of(appUserWithCommute));

        when(commuteMapper.mapCommuteDtoToCommuteEntities(any(CommuteDto.class), eq(appUserWithCommute)))
                .thenReturn(commuteEntity);

        when(commuteRepository.save(commuteEntity))
                .thenReturn(commuteEntity);

        when(commuteMapper.mapCommuteEntityToCommuteDto(commuteEntity))
                .thenReturn(commuteDto);

        CommuteDto result = addCommuteService.addCommute(addCommuteDto, commutingAppUserEmail);

        verify(appUserRepository).findByAppUserEmail(commutingAppUserEmail);
        verify(commuteMapper).mapCommuteDtoToCommuteEntities(any(CommuteDto.class), eq(appUserWithCommute));
        verify(commuteRepository).save(commuteEntity);
        verify(commuteMapper).mapCommuteEntityToCommuteDto(commuteEntity);
        assertNotNull(result);
        assertEquals(commuteStopHour, result.getCommuteStopHour());
        assertEquals(commuteStartHour, result.getCommuteStartHour());
        assertEquals(commuteTripsPerWeek, result.getCommuteTripsPerWeek());
        assertEquals(meansOfTransports, result.getCommuteMeansOfTransport());
        assertEquals(commutingAppUserEmail, result.getCommutingAppUserEmail());
    }

    @Test
    void should_throw_when_add_commute_dto_is_null() {
        String commutingAppUserEmail = "marek@gmail.com";
        assertThrows(IllegalArgumentException.class,
                () -> addCommuteService.addCommute(null, commutingAppUserEmail));
        verifyNoInteractions(appUserRepository, commuteRepository, commuteMapper);
    }

    @Test
    void should_throw_when_app_user_not_found_by_email() {
        String wrongAppUserEmail = "wrong.email@gmail.com";
        AddCommuteDto addCommuteDto = AddCommuteDto.builder().build();

        when(appUserRepository.findByAppUserEmail(wrongAppUserEmail))
                .thenReturn(Optional.empty());

        assertThrows(AppUserNotFoundByEmailException.class,
                () -> addCommuteService.addCommute(addCommuteDto, wrongAppUserEmail));

        verify(appUserRepository).findByAppUserEmail(wrongAppUserEmail);
        verifyNoInteractions(commuteRepository, commuteMapper);
    }

}