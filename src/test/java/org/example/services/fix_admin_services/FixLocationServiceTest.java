package org.example.services.fix_admin_services;

import org.example.dtos.location.UpdateLocationDto;
import org.example.entities.Location;
import org.example.repositories.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FixLocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private FixLocationService fixLocationService;

//    @Test
//    void should_fix_invalid_location_and_save() {
//        double oldLat = 52.40;
//        double oldLng = 16.90;
//
//        double newLat = 52.401;
//        double newLng = 16.901;
//
//        Location existing = Location.builder()
//                .id(10L)
//                .locationLatitude(oldLat)
//                .locationLongitude(oldLng)
//                .build();
//
//        UpdateLocationDto dto = UpdateLocationDto.builder()
//                .locationLatitude(newLat)
//                .locationLongitude(newLng)
//                .build();
//
//        when(locationRepository.findByLocationLatitudeAndLocationLongitude(oldLat, oldLng))
//                .thenReturn(Optional.of(existing));
//
//        when(locationRepository.save(existing)).thenReturn(existing);
//
//        fixLocationService.fixInvalidLocation(oldLng, oldLat , dto);
//
//        assertEquals(newLat, existing.getLocationLatitude());
//        assertEquals(newLng, existing.getLocationLongitude());
//
//        verify(locationRepository).findByLocationLatitudeAndLocationLongitude(oldLat, oldLng);
//        verify(locationRepository).save(existing);
//        verifyNoMoreInteractions(locationRepository);
//    }
}
