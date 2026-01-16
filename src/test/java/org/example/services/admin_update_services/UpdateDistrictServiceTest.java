package org.example.services.admin_update_services;

import org.example.dtos.district.DistrictDto;
import org.example.dtos.district.UpdateDistrictDto;
import org.example.entities.City;
import org.example.entities.District;
import org.example.exceptions.CityNotFoundByNameException;
import org.example.mappers.DistrictMapper;
import org.example.repositories.CityRepository;
import org.example.repositories.DistrictRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateDistrictServiceTest {

    @Mock DistrictRepository districtRepository;
    @Mock CityRepository cityRepository;
    @Mock DistrictMapper districtMapper;

    @InjectMocks UpdateDistrictService updateDistrictService;

    @Test
    void should_update_district() {
        // given
        Long districtId = 1L;

        UpdateDistrictDto dto = UpdateDistrictDto.builder()
                .districtName("Nowa Nazwa")
                .districtCity("Warszawa")
                .districtZipCode("00-001")
                .districtResidentsCount(12345L)
                .build();

        City city = City.builder()
                .id(10L)
                .cityName("Warszawa")
                .build();

        District district = District.builder()
                .id(districtId)
                .districtName("Stara Nazwa")
                .districtZipCode("11-111")
                .districtResidentsCount(1L)
                .build();

        DistrictDto mappedDto = DistrictDto.builder()
                .districtName("Nowa Nazwa")
                .districtCity("Warszawa")
                .districtZipCode("00-001")
                .districtResidentsCount(12345L)
                .build();

        when(districtRepository.findById(districtId)).thenReturn(Optional.of(district));
        when(cityRepository.findByCityName("Warszawa")).thenReturn(Optional.of(city));
        when(districtMapper.mapDistrictEntityToDistrictDto(district)).thenReturn(mappedDto);

        // when
        DistrictDto result = updateDistrictService.updateDistrictById(districtId, dto);

        // then — sprawdzamy, że encja została zmieniona
        assertEquals("Nowa Nazwa", district.getDistrictName());
        assertEquals(city, district.getDistrictCity());
        assertEquals("00-001", district.getDistrictZipCode());
        assertEquals(12345L, district.getDistrictResidentsCount());

        // then — i że wynik DTO jest sensowny (z mappera)
        assertNotNull(result);
        assertEquals("Nowa Nazwa", result.getDistrictName());
        assertEquals("Warszawa", result.getDistrictCity());
        assertEquals("00-001", result.getDistrictZipCode());
        assertEquals(12345L, result.getDistrictResidentsCount());

        // verify flow
        verify(districtRepository).findById(districtId);
        verify(cityRepository).findByCityName("Warszawa");
        verify(districtRepository).save(district);
        verify(districtMapper).mapDistrictEntityToDistrictDto(district);
        verifyNoMoreInteractions(districtRepository, cityRepository, districtMapper);
    }

    @Test
    void should_throw_when_city_not_found() {
        // given
        Long districtId = 1L;

        UpdateDistrictDto dto = UpdateDistrictDto.builder()
                .districtName("Nowa Nazwa")
                .districtCity("NieIstnieje")
                .districtZipCode("00-001")
                .districtResidentsCount(12345L)
                .build();

        District district = District.builder()
                .id(districtId)
                .districtName("Stara Nazwa")
                .build();

        when(districtRepository.findById(districtId)).thenReturn(Optional.of(district));
        when(cityRepository.findByCityName("NieIstnieje")).thenReturn(Optional.empty());

        // when + then
        assertThrows(CityNotFoundByNameException.class,
                () -> updateDistrictService.updateDistrictById(districtId, dto));

        // verify: nie zapisujemy district i nie mapujemy DTO
        verify(districtRepository).findById(districtId);
        verify(cityRepository).findByCityName("NieIstnieje");
        verify(districtRepository, never()).save(any());
        verifyNoInteractions(districtMapper);
    }
}
