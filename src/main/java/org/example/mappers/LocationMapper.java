package org.example.mappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.entities.Location;
import org.springframework.stereotype.Component;

@Builder
@Component
@AllArgsConstructor
public class LocationMapper {

    //TODO DODANIE GEO MAPPOWANIA NA LOKACJĘ
    //TODO w tym miejscu użytkownik dostawać będzie alert o tym że w danej lokacji już istnieje jakiś art piece.
    public Location mapAddressToLocationEntity(String address) {
        return Location.builder()
                .locationLongitude(0)
                .locationLatitude(0)
                .locationHeight(0)
                .build();
    }
}
