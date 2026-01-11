package org.example.services.update_services;


import lombok.RequiredArgsConstructor;
import org.example.dtos.commute.CommuteDto;
import org.example.dtos.user.AppUserDto;
import org.example.dtos.user.UpdateAppUserDto;
import org.example.entities.AppUser;
import org.example.entities.City;
import org.example.entities.Commute;
import org.example.entities.District;
import org.example.exceptions.AppUserNotFoundByEmailException;
import org.example.exceptions.CityNotFoundException;
import org.example.exceptions.DistrictNotFoundByNameException;
import org.example.mappers.AppUserMapper;
import org.example.mappers.CommuteMapper;
import org.example.repositories.AppUserRepository;
import org.example.repositories.CityRepository;
import org.example.repositories.DistrictRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UpdateAppUserService {

    private final AppUserRepository appUserRepository;
    private final CityRepository cityRepository;
    private final CommuteMapper commuteMapper;
    private final DistrictRepository districtRepository;
    private final AppUserMapper appUserMapper;

    public AppUserDto updateAppUserByEmail(UpdateAppUserDto updateUserDto, String appUserBeingUpdatedEmail) {

        AppUser appUserBeingUpdated = appUserRepository.findByAppUserEmail(appUserBeingUpdatedEmail)
                .orElseThrow(() -> new AppUserNotFoundByEmailException(appUserBeingUpdatedEmail));

        String updatedCityName = updateUserDto.getAppUserCity();
        City updatedCityEntity = cityRepository.findByCityName(updatedCityName)
                .orElseThrow(() -> new CityNotFoundException(updatedCityName));
        String updatedDistrictName = updateUserDto.getAppUserLiveInDistrict();
        District updatedDistrictEntity = districtRepository.findByDistrictName(updatedDistrictName)
                .orElseThrow(() -> new DistrictNotFoundByNameException(updatedDistrictName));

        Set<CommuteDto> updatedCommuteDtos = updateUserDto.getAppUserCommuteDtos();
        Set<Commute> updatedCommuteEntities = new HashSet<>();
        for (CommuteDto commuteDto : updatedCommuteDtos) {
            Commute updatedCommuteEntity = commuteMapper.mapCommuteDtoToCommuteEntities(commuteDto, appUserBeingUpdated);
            updatedCommuteEntities.add(updatedCommuteEntity);
        }

        appUserBeingUpdated.setAppUserName(updateUserDto.getAppUserName());
        appUserBeingUpdated.setAppUserPassword(updateUserDto.getAppUserPassword());
        appUserBeingUpdated.setAppUserEmail(updateUserDto.getAppUserEmail());
        appUserBeingUpdated.setAppUserCity(updatedCityEntity);
        appUserBeingUpdated.setAppUserCommutes(updatedCommuteEntities);
        appUserBeingUpdated.setAppUserLiveInDistrict(updatedDistrictEntity);
        appUserBeingUpdated.setAppUserLanguagesSpoken(updateUserDto.getAppUserLanguagesSpoken());

        appUserRepository.save(appUserBeingUpdated);
        return appUserMapper.mapAppUserEntityToAppUserDto(appUserBeingUpdated);
    }
}

