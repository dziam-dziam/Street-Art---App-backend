package org.example.mappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.dtos.city.CityDto;
import org.example.entities.City;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
public class CityMapper {
    public City mapCityDtoToCityEntity(CityDto cityDto) {
        if (cityDto == null) throw new IllegalArgumentException("CityDto is null");

        return City.builder()
                .cityName(cityDto.getCityName())
                .cityResidentsCount(cityDto.getCityResidentsCount())
                .build();
    }

    public CityDto mapCityEntityToCityDto(City cityEntity) {
        if (cityEntity == null) throw new IllegalArgumentException("CityEntity is null");

        return CityDto.builder()
                .cityName(cityEntity.getCityName())
                .cityResidentsCount(cityEntity.getCityResidentsCount())
                .build();
    }
}
