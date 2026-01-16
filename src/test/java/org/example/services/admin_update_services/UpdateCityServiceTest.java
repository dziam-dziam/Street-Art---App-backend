package org.example.services.admin_update_services;

import org.example.dtos.city.CityDto;
import org.example.dtos.city.UpdateCityDto;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCityServiceTest {

    @Mock CityRepository cityRepository;
    @Mock CityMapper cityMapper;

    @InjectMocks UpdateCityService updateCityService;

    @Test
    void should_update_city_by_id() {
        City city = City.builder().id(1L).cityName("Old").cityResidentsCount(1L).build();
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        UpdateCityDto updateDto = UpdateCityDto.builder().cityName("New").cityResidentsCount(999L).build();

        CityDto mapped = CityDto.builder().cityName("New").cityResidentsCount(999L).build();
        when(cityMapper.mapCityEntityToCityDto(city)).thenReturn(mapped);

        CityDto result = updateCityService.updateCityById(1L, updateDto);

        assertEquals("New", city.getCityName());
        assertEquals(999L, city.getCityResidentsCount());
        assertEquals("New", result.getCityName());

        verify(cityRepository).save(city);
    }
}
