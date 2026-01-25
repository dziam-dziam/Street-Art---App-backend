package org.example.services.get_services.get_all_services;

import org.example.dtos.district.DistrictAdminDto;
import org.example.dtos.district.DistrictDto;
import org.example.entities.District;
import org.example.mappers.DistrictMapper;
import org.example.repositories.DistrictRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllDistrictsServiceTest {

    @Mock DistrictRepository districtRepository;
    @Mock DistrictMapper districtMapper;

    @InjectMocks GetAllDistrictsService getAllDistrictsService;

    @Test
    void should_return_list_of_districtDtos_when_districts_exist() {
        District d1 = District.builder().districtName("Jeżyce").build();
        District d2 = District.builder().districtName("Wilda").build();

        when(districtRepository.findAll()).thenReturn(List.of(d1, d2));

        when(districtMapper.mapDistrictEntityToAdminDto(d1))
                .thenReturn(DistrictAdminDto.builder().districtName("Jeżyce").build());
        when(districtMapper.mapDistrictEntityToAdminDto(d2))
                .thenReturn(DistrictAdminDto.builder().districtName("Wilda").build());

        List<DistrictAdminDto> result = getAllDistrictsService.getAllDistrict();

        assertEquals(2, result.size());
        assertEquals("Jeżyce", result.get(0).getDistrictName());
        assertEquals("Wilda", result.get(1).getDistrictName());

        verify(districtRepository).findAll();
        verify(districtMapper).mapDistrictEntityToAdminDto(d1);
        verify(districtMapper).mapDistrictEntityToAdminDto(d2);
    }

    @Test
    void should_return_empty_list_when_no_districts() {
        when(districtRepository.findAll()).thenReturn(List.of());

        List<DistrictAdminDto> result = getAllDistrictsService.getAllDistrict();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(districtRepository).findAll();
        verifyNoInteractions(districtMapper);
    }
}
