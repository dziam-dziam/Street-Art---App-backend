package org.example.services.update_services;


import lombok.RequiredArgsConstructor;
import org.example.dtos.commute.CommuteDto;
import org.example.dtos.app_user.AppUserDto;
import org.example.dtos.app_user.UpdateAppUserDto;
import org.example.entities.AppUser;
import org.example.entities.Commute;
import org.example.exceptions.AppUserNotFoundByEmailException;
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

    //TODO finals
    public AppUserDto updateAppUserByEmail(UpdateAppUserDto updateUserDto, String appUserBeingUpdatedEmail) {

        AppUser appUserBeingUpdated = appUserRepository.findByAppUserEmail(appUserBeingUpdatedEmail)
                .orElseThrow(() -> new AppUserNotFoundByEmailException(appUserBeingUpdatedEmail));

        //TODO po co zmienna z geta? bez sensu
        Set<CommuteDto> updatedCommuteDtos = updateUserDto.getAppUserCommuteDtos();

        //TODO wynies ten fragment kodu do metody dobrze opisanej
        Set<Commute> updatedCommuteEntities = new HashSet<>();
        if (updatedCommuteDtos != null) {
            for (CommuteDto commuteDto : updatedCommuteDtos) {
                Commute updatedCommuteEntity = commuteMapper
                        .mapCommuteDtoToCommuteEntities(commuteDto, appUserBeingUpdated);
                updatedCommuteEntities.add(updatedCommuteEntity);
            }
        }

        appUserBeingUpdated.setAppUserName(updateUserDto.getAppUserName());
        appUserBeingUpdated.setAppUserPassword(updateUserDto.getAppUserPassword());
        appUserBeingUpdated.setAppUserEmail(updateUserDto.getAppUserEmail());
//        appUserBeingUpdated.setAppUserCity(updatedCityEntity);
//        appUserBeingUpdated.setAppUserLiveInDistrict(updatedDistrictEntity);
        appUserBeingUpdated.setAppUserLanguagesSpoken(updateUserDto.getAppUserLanguagesSpoken());

        appUserBeingUpdated.getAppUserCommutes().clear();
        for (Commute commute : updatedCommuteEntities) {
            appUserBeingUpdated.addCommute(commute);
        }

        appUserRepository.save(appUserBeingUpdated);
        return appUserMapper.mapAppUserEntityToAppUserDto(appUserBeingUpdated);
    }
}

