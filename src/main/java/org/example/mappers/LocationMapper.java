package org.example.mappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.dtos.location.LocationDto;
import org.springframework.stereotype.Component;

@Builder
@Component
@AllArgsConstructor
public class LocationMapper {

    //TODO tymczasowa propozycja po adressie mapowanie
    public LocationDto mapAddressToLocationDto(String address) {
        return LocationDto.builder()
                .locationLongitude(0)
                .locationLatitude(0)
                .locationHeight(0)
                .build();
    }
}
