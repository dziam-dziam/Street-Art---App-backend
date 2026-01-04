package org.example.mappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.dtos.city.CityDto;
import org.example.dtos.district.DistrictDto;
import org.example.entities.City;
import org.example.entities.District;
import org.example.exceptions.DistrictNotFoundByZipCodeException;
import org.example.repositories.DistrictRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Builder
@AllArgsConstructor
public class CityMapper {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;


    public City mapCityDtoToCityEntity(CityDto cityDto) {
        if (cityDto == null) throw new IllegalArgumentException("CityDto is null");

        Set<District> cityDtoDistrictEntities = new HashSet<>();
        Set<DistrictDto> cityDtoDistrictDtos = cityDto.getCityDistricts();

        for (DistrictDto districtDto : cityDtoDistrictDtos) {
            String districtDtoZipCode = districtDto.getDistrictZipCode();
            District cityDtoFoundDistrictEntity = districtRepository.findByDistrictZipCode(districtDtoZipCode)
                    .orElseThrow(() -> new DistrictNotFoundByZipCodeException(districtDtoZipCode));
            cityDtoDistrictEntities.add(cityDtoFoundDistrictEntity);
        }

        return City.builder()
                .cityName(cityDto.getCityName())
                .cityResidentsCount(cityDto.getCityResidentsCount())
                .cityDistricts(cityDtoDistrictEntities)
                .build();
    }

    public CityDto mapCityEntityToCityDto(City cityEntity) {
        if (cityEntity == null) throw new IllegalArgumentException("CityEntity is null");

        Set<District> cityEntityDistrictEntities = cityEntity.getCityDistricts();
        Set<DistrictDto> cityEntityDistrictDtos = new HashSet<>();
        for (District districtEntity : cityEntityDistrictEntities) {
            DistrictDto districtDto = districtMapper.mapDistrictEntityToDistrictDto(districtEntity);
            cityEntityDistrictDtos.add(districtDto);
        }
        return CityDto.builder()
                .cityName(cityEntity.getCityName())
                .cityResidentsCount(cityEntity.getCityResidentsCount())
                .cityDistricts(cityEntityDistrictDtos)
                .build();
    }
}
