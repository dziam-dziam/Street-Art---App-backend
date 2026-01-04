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
        List<CityDto> cityDtos = new ArrayList<>();
        List<City> cityEntities = cityRepository.findAll();
        for (City cityEntity : cityEntities) {
            Set<District> districts = cityEntity.getCityDistricts();
            Set<DistrictDto> districtDtos = new HashSet<>();
            for (District district : districts) {
                DistrictDto districtDto = districtMapper.mapDistrictEntityToDistrictDto(district);
                districtDtos.add(districtDto);
            }
            CityDto cityDto = cityMapper.mapCityEntityToCityDto(cityEntity);
            cityDto.setCityDistricts(districtDtos);
            cityDtos.add(cityDto);
        }
        return cityDtos;
    }
}
