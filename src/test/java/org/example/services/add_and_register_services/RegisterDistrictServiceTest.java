package org.example.services.add_and_register_services;

import org.example.dtos.district.AddDistrictDto;
import org.example.dtos.district.DistrictDto;
import org.example.entities.District;
import org.example.mappers.DistrictMapper;
import org.example.repositories.DistrictRepository;
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
class RegisterDistrictServiceTest {

    @Mock
    private DistrictRepository districtRepository;
    @Mock
    private DistrictMapper districtMapper;

    @InjectMocks
    private RegisterDistrictService registerDistrictService;

    @Test
    void should_register_district_when_not_exists() {
        AddDistrictDto dto = AddDistrictDto.builder()
                .districtName("Jeżyce")
                .districtCity("Poznań")
                .districtZipCode("60-101")
                .districtResidentsCount(100_000L)
                .build();

        when(districtRepository.findByDistrictName("Jeżyce")).thenReturn(Optional.empty());

        District districtEntity = District.builder().id(1L).districtName("Jeżyce").build();
        when(districtMapper.mapDistrictDtoToDistrictEntity(any(DistrictDto.class))).thenReturn(districtEntity);
        when(districtRepository.save(districtEntity)).thenReturn(districtEntity);

        DistrictDto expected = DistrictDto.builder()
                .districtName("Jeżyce")
                .districtCity("Poznań")
                .districtZipCode("60-101")
                .districtResidentsCount(100_000L)
                .districtArtPiecesCount(0L)
                .build();
        when(districtMapper.mapDistrictEntityToDistrictDto(districtEntity)).thenReturn(expected);

        DistrictDto result = registerDistrictService.createDistrict(dto);

        assertNotNull(result);
        assertEquals("Jeżyce", result.getDistrictName());
        assertEquals(0L, result.getDistrictArtPiecesCount());

        verify(districtRepository).findByDistrictName("Jeżyce");
        verify(districtRepository).save(districtEntity);
        verify(districtMapper).mapDistrictDtoToDistrictEntity(any(DistrictDto.class));
        verify(districtMapper).mapDistrictEntityToDistrictDto(districtEntity);
    }

    @Test
    void should_throw_when_add_district_dto_is_null() {
        assertThrows(IllegalArgumentException.class, () -> registerDistrictService.createDistrict(null));
        verifyNoInteractions(districtRepository, districtMapper);
    }

    @Test
    void should_throw_when_district_already_exists() {
        AddDistrictDto dto = AddDistrictDto.builder().districtName("Jeżyce").build();
        when(districtRepository.findByDistrictName("Jeżyce")).thenReturn(Optional.of(new District()));

        assertThrows(IllegalArgumentException.class, () -> registerDistrictService.createDistrict(dto));

        verify(districtRepository).findByDistrictName("Jeżyce");
        verify(districtRepository, never()).save(any());
        verifyNoInteractions(districtMapper);
    }
}
