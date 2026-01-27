package org.example.services.get_services.get_all_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.city.CityDto;
import org.example.dtos.district.DistrictDto;
import org.example.entities.City;
import org.example.entities.District;
import org.example.mappers.CityMapper;
import org.example.mappers.DistrictMapper;
import org.example.repositories.CityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetAllCitiesService {

    private final CityRepository cityRepository;
    private final DistrictMapper districtMapper;
    private final CityMapper cityMapper;

    public List<CityDto> getAllCities() {
        final List<City> cityEntities = cityRepository.findAll();
        return mapCityEntitiesToDtos(cityEntities);
    }


    private Set<DistrictDto> mapDistrictEntitiesToDtos(Set<District> districts) {
        final Set<DistrictDto> districtDtos = new HashSet<>();
        for (District district : districts) {
            final DistrictDto districtDto = districtMapper.mapDistrictEntityToDistrictDto(district);
            districtDtos.add(districtDto);
        }
        return districtDtos;
    }

    private List<CityDto> mapCityEntitiesToDtos(List<City> cityEntities) {
        final List<CityDto> cityDtos = new ArrayList<>();

        for (City cityEntity : cityEntities) {
            final Set<District> districts = cityEntity.getCityDistricts();
            final Set<DistrictDto> districtDtos = mapDistrictEntitiesToDtos(districts);
            final CityDto cityDto = cityMapper.mapCityEntityToCityDto(cityEntity);
            cityDto.setCityDistricts(districtDtos);
            cityDtos.add(cityDto);
        }

        return cityDtos;
    }
}
