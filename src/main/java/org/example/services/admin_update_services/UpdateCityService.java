package org.example.services.admin_update_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.city.CityAdminDto;
import org.example.dtos.city.CityDto;
import org.example.dtos.city.UpdateCityDto;
import org.example.entities.City;
import org.example.exceptions.CityNotFoundByIdException;
import org.example.mappers.CityMapper;
import org.example.repositories.CityRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityAdminDto updateCityById(Long cityId, UpdateCityDto updateCityDto) {
        City cityEntityToBeUpdated = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundByIdException(cityId));

        cityEntityToBeUpdated.setCityName(updateCityDto.getCityName());
        cityEntityToBeUpdated.setCityResidentsCount(updateCityDto.getCityResidentsCount());

        cityRepository.save(cityEntityToBeUpdated);

        return cityMapper.mapCityEntityToAdminDto(cityEntityToBeUpdated);
    }
}
