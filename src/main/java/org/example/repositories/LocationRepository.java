package org.example.repositories;

import org.example.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByLocationLatitudeAndLocationLongitude(
            double locationLatitude,
            double locationLongitude
    );
}
