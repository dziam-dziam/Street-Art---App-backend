package org.example.services.fix_admin_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.location.UpdateLocationDto;
import org.example.entities.Location;
import org.example.exceptions.LocationNotFoundByLonLatException;
import org.example.repositories.LocationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FixLocationService {


    private final LocationRepository locationRepository;

    //TODO finale!
    public Location fixInvalidLocation(double locationLongitude, double locationLatitude,
                                       UpdateLocationDto updateLocationDto) {
        Location locationToBeFixed = locationRepository
                .findByLocationLatitudeAndLocationLongitude(locationLatitude, locationLongitude)
                .orElseThrow(() -> new LocationNotFoundByLonLatException(locationLatitude, locationLongitude));

        double newLocationLatitude = updateLocationDto.getLocationLatitude();
        double newLocationLongitude = updateLocationDto.getLocationLongitude();

        locationToBeFixed.setLocationLatitude(newLocationLatitude);
        locationToBeFixed.setLocationLongitude(newLocationLongitude);

        locationRepository.save(locationToBeFixed);

        return locationToBeFixed;
    }
}
