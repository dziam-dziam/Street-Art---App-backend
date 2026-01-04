package org.example.services.add_and_register_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.city.CityDto;
import org.example.dtos.city.AddCityDto;
import org.example.entities.City;
import org.example.mappers.CityMapper;
import org.example.repositories.CityRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class RegisterCityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityDto createCity(AddCityDto addCityDto) {
        if (addCityDto == null) throw new IllegalArgumentException("AddCityDto is null");
        if (addCityDto.getCityName() == null) throw new IllegalArgumentException("AddCityDto city name is null");

        String addCityDtoCityName = addCityDto.getCityName();

        if (cityRepository.findByCityName(addCityDtoCityName).isPresent()) {
            throw new IllegalArgumentException("City with name: " + addCityDtoCityName + " already  exists");
        }

        CityDto cityDto = CityDto.builder()
                .cityName(addCityDtoCityName)
                .cityResidentsCount(addCityDto.getCityResidentsCount())
                .cityDistricts(new HashSet<>())
                .build();

        City city = cityMapper.mapCityDtoToCityEntity(cityDto);
        City saved = cityRepository.save(city);

        return cityMapper.mapCityEntityToCityDto(saved);
    }
}
