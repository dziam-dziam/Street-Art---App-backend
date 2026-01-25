package org.example.services.get_services.get_by_name_services;

import org.example.entities.District;
import org.example.exceptions.DistrictNotFoundByNameException;
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
class GetDistrictByNameServiceTest {

    @Mock DistrictRepository districtRepository;

    @InjectMocks GetDistrictByNameService getDistrictByNameService;

    @Test
    void should_return_district_when_found_by_name() {
        District district = District.builder().districtName("Jeżyce").build();
        when(districtRepository.findByDistrictName("Jeżyce"))
                .thenReturn(Optional.of(district));

        District result = getDistrictByNameService.getDistrictByName("Jeżyce");

        assertNotNull(result);
        assertEquals("Jeżyce", result.getDistrictName());
        verify(districtRepository).findByDistrictName("Jeżyce");
    }

    @Test
    void should_throw_when_district_not_found_by_name() {
        when(districtRepository.findByDistrictName("Nope"))
                .thenReturn(Optional.empty());

        assertThrows(DistrictNotFoundByNameException.class,
                () -> getDistrictByNameService.getDistrictByName("Nope"));

        verify(districtRepository).findByDistrictName("Nope");
    }
}
