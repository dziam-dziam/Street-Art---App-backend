package org.example.services.admin_update_services;

import org.example.dtos.city.CityAdminDto;
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

    @Mock
    private CityRepository cityRepository;
    @Mock
    private CityMapper cityMapper;

    @InjectMocks
    private UpdateCityService updateCityService;

    @Test
    void should_update_city_by_id() {
        City city = City.builder().id(1L).cityName("Old").build();
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        UpdateCityDto updateDto = UpdateCityDto.builder().cityName("New").build();

        CityAdminDto mapped = CityAdminDto.builder().cityName("New").build();
        when(cityMapper.mapCityEntityToAdminDto(city)).thenReturn(mapped);

        CityAdminDto result = updateCityService.updateCityById(1L, updateDto);

        assertEquals("New", city.getCityName());
        assertEquals("New", result.getCityName());

        verify(cityRepository).save(city);
    }
}
