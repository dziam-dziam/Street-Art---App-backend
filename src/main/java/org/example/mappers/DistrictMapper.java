package org.example.mappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.dtos.district.DistrictDto;
import org.example.entities.City;
import org.example.entities.District;
import org.example.exceptions.CityNotFoundException;
import org.example.repositories.CityRepository;
import org.example.repositories.DistrictRepository;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
public class DistrictMapper {

    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;

    public District mapDistrictDtoToDistrictEntity(DistrictDto districtDto) {
        if (districtDto == null) throw new IllegalArgumentException("DistrictDto is null");

        String districtCityName = districtDto.getDistrictCity();
        City cityFromDto = cityRepository.findByCityName(districtCityName)
                .orElseThrow(() -> new CityNotFoundException(districtCityName));

        return District.builder()
                .districtName(districtDto.getDistrictName())
                .districtCity(cityFromDto)
                .districtArtPiecesCount(districtDto.getDistrictArtPiecesCount())
                .districtResidentsCount(districtDto.getDistrictResidentsCount())
                .build();
    }

    public DistrictDto mapDistrictEntityToDistrictDto(District districtEntity) {
        if (districtEntity == null) throw new IllegalArgumentException("DistrictEntity is null");

        City districtEntityCity = districtEntity.getDistrictCity();
        String districtEntityCityName = districtEntityCity.getCityName();

        return DistrictDto.builder()
                .districtCity(districtEntityCityName)
                .districtName(districtEntity.getDistrictName())
                .districtArtPiecesCount(districtEntity.getDistrictArtPiecesCount())
                .districtResidentsCount(districtEntity.getDistrictResidentsCount())
                .build();
    }
}
