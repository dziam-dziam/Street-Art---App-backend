package org.example.mappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.entities.Location;
import org.example.integrations.geocoding.GeocodeResult;
import org.example.repositories.LocationRepository;
import org.example.services.geocoding_services.GeocodingService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Builder
@Component
@AllArgsConstructor
public class LocationMapper {

    //TODO w tym miejscu użytkownik dostawać będzie alert o tym że w danej lokacji już istnieje jakiś art piece.
    private final GeocodingService geocodingService;
    private final LocationRepository locationRepository;

    public Location mapAddressToLocationEntity(String address, String city) {
        if (address == null || address.isBlank()) throw new IllegalArgumentException("Address is null/blank");

        String query = (city == null || city.isBlank())
                ? address.trim()
                : address.trim() + ", " + city.trim();

        GeocodeResult geocodeResult = geocodingService.geocode(query);

        double geocodeResultLatitude = geocodeResult.getLatitude();
        double geocodeResultLongitude = geocodeResult.getLongitude();

        double latitude = round(geocodeResultLatitude);
        double longitude = round(geocodeResultLongitude);

        return locationRepository
                .findByLocationLatitudeAndLocationLongitude(latitude,longitude)
                .orElseGet(() -> Location.builder()
                        .locationLongitude(longitude)
                        .locationLatitude(latitude)
                        .build());
    }

    private double round(double value){
        return BigDecimal.valueOf(value).setScale(6, RoundingMode.HALF_UP).doubleValue();
    }

}
