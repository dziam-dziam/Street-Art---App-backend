package org.example.services.get_services.get_all_services;

import org.example.dtos.city.CityDto;
import org.example.dtos.district.DistrictDto;
import org.example.entities.City;
import org.example.entities.District;
import org.example.mappers.CityMapper;
import org.example.mappers.DistrictMapper;
import org.example.repositories.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllCitiesServiceTest {

    @Mock CityRepository cityRepository;
    @Mock DistrictMapper districtMapper;
    @Mock CityMapper cityMapper;

    @InjectMocks GetAllCitiesService getAllCitiesService;

    @Test
    void should_return_cityDtos_with_mapped_districts() {
        // given
        District d1 = District.builder().districtName("Jeżyce").build();
        District d2 = District.builder().districtName("Grunwald").build();

        Set<District> cityDistricts = new HashSet<>();
        cityDistricts.add(d1);
        cityDistricts.add(d2);

        City city = City.builder()
                .cityName("Poznań")
                .cityDistricts(cityDistricts)
                .build();

        when(cityRepository.findAll()).thenReturn(List.of(city));

        when(districtMapper.mapDistrictEntityToDistrictDto(d1))
                .thenReturn(DistrictDto.builder().districtName("Jeżyce").build());
        when(districtMapper.mapDistrictEntityToDistrictDto(d2))
                .thenReturn(DistrictDto.builder().districtName("Grunwald").build());

        // uwaga: mapper zwraca CityDto bez districts, a serwis je ustawia setCityDistricts(...)
        when(cityMapper.mapCityEntityToCityDto(city))
                .thenReturn(CityDto.builder().cityName("Poznań").build());

        // when
        List<CityDto> result = getAllCitiesService.getAllCities();

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Poznań", result.get(0).getCityName());
        assertNotNull(result.get(0).getCityDistricts());
        assertEquals(2, result.get(0).getCityDistricts().size());

        verify(cityRepository).findAll();
        verify(cityMapper).mapCityEntityToCityDto(city);
        verify(districtMapper).mapDistrictEntityToDistrictDto(d1);
        verify(districtMapper).mapDistrictEntityToDistrictDto(d2);
        verifyNoMoreInteractions(cityRepository, cityMapper, districtMapper);
    }

    @Test
    void should_return_empty_list_when_no_cities() {
        // given
        when(cityRepository.findAll()).thenReturn(List.of());

        // when
        List<CityDto> result = getAllCitiesService.getAllCities();

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(cityRepository).findAll();
        verifyNoInteractions(cityMapper, districtMapper);
    }
}
