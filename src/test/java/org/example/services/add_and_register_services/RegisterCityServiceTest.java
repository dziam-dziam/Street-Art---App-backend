package org.example.services.add_and_register_services;

import org.example.dtos.city.AddCityDto;
import org.example.dtos.city.CityDto;
import org.example.entities.City;
import org.example.mappers.CityMapper;
import org.example.repositories.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterCityServiceTest {

    @Mock CityRepository cityRepository;
    @Mock CityMapper cityMapper;

    @InjectMocks RegisterCityService registerCityService;

    @Test
    void should_register_city_when_not_exists() {
        AddCityDto dto = AddCityDto.builder()
                .cityName("Poznań")
                .cityResidentsCount(500_000L)
                .build();

        when(cityRepository.findByCityName("Poznań")).thenReturn(Optional.empty());

        City cityEntity = City.builder().id(1L).cityName("Poznań").cityResidentsCount(500_000L).build();
        when(cityMapper.mapCityDtoToCityEntity(any(CityDto.class))).thenReturn(cityEntity);
        when(cityRepository.save(cityEntity)).thenReturn(cityEntity);

        CityDto expected = CityDto.builder().cityName("Poznań").cityResidentsCount(500_000L).build();
        when(cityMapper.mapCityEntityToCityDto(cityEntity)).thenReturn(expected);

        CityDto result = registerCityService.createCity(dto);

        assertNotNull(result);
        assertEquals("Poznań", result.getCityName());
        assertEquals(500_000L, result.getCityResidentsCount());

        verify(cityRepository).findByCityName("Poznań");
        verify(cityRepository).save(cityEntity);
        verify(cityMapper).mapCityDtoToCityEntity(any(CityDto.class));
        verify(cityMapper).mapCityEntityToCityDto(cityEntity);
    }

    @Test
    void should_throw_when_add_city_dto_is_null() {
        assertThrows(IllegalArgumentException.class, () -> registerCityService.createCity(null));
        verifyNoInteractions(cityRepository, cityMapper);
    }

    @Test
    void should_throw_when_city_name_is_null() {
        AddCityDto dto = AddCityDto.builder().cityName(null).build();
        assertThrows(IllegalArgumentException.class, () -> registerCityService.createCity(dto));
        verifyNoInteractions(cityRepository, cityMapper);
    }

    @Test
    void should_throw_when_city_already_exists() {
        AddCityDto dto = AddCityDto.builder().cityName("Poznań").build();
        when(cityRepository.findByCityName("Poznań")).thenReturn(Optional.of(new City()));

        assertThrows(IllegalArgumentException.class, () -> registerCityService.createCity(dto));

        verify(cityRepository).findByCityName("Poznań");
        verify(cityRepository, never()).save(any());
        verifyNoInteractions(cityMapper);
    }
}
