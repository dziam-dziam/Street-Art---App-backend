package org.example.services.update_services;


import lombok.RequiredArgsConstructor;
import org.example.dtos.commute.CommuteDto;
import org.example.dtos.app_user.AppUserDto;
import org.example.dtos.app_user.UpdateAppUserDto;
import org.example.entities.AppUser;
import org.example.entities.Commute;
import org.example.entities.District;
import org.example.exceptions.AppUserNotFoundByEmailException;
import org.example.exceptions.DistrictNotFoundByNameException;
import org.example.mappers.AppUserMapper;
import org.example.mappers.CommuteMapper;
import org.example.repositories.AppUserRepository;
import org.example.repositories.DistrictRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UpdateAppUserService {

    private final AppUserRepository appUserRepository;
    private final CommuteMapper commuteMapper;
    private final DistrictRepository districtRepository;
    private final AppUserMapper appUserMapper;

    public AppUserDto updateAppUserByEmail(UpdateAppUserDto updateUserDto, String appUserBeingUpdatedEmail) {

        final AppUser appUserBeingUpdated = appUserRepository.findByAppUserEmail(appUserBeingUpdatedEmail)
                .orElseThrow(() -> new AppUserNotFoundByEmailException(appUserBeingUpdatedEmail));

        final Set<Commute> updatedCommuteEntities = updateCommutes(updateUserDto,appUserBeingUpdated);

        if (updateUserDto.getAppUserLiveInDistrict() != null) {
            final District districtEntity = districtRepository.findByDistrictName(updateUserDto.getAppUserLiveInDistrict())
                    .orElseThrow(() -> new DistrictNotFoundByNameException(updateUserDto.getAppUserLiveInDistrict()));
            appUserBeingUpdated.setAppUserLiveInDistrict(districtEntity);
        }

        appUserBeingUpdated.setAppUserName(updateUserDto.getAppUserName());
        appUserBeingUpdated.setAppUserPassword(updateUserDto.getAppUserPassword());
        appUserBeingUpdated.setAppUserEmail(updateUserDto.getAppUserEmail());
        appUserBeingUpdated.setAppUserLanguagesSpoken(updateUserDto.getAppUserLanguagesSpoken());
        appUserBeingUpdated.getAppUserCommutes().clear();
        for (Commute commute : updatedCommuteEntities) {
            appUserBeingUpdated.addCommute(commute);
        }

        appUserRepository.save(appUserBeingUpdated);
        return appUserMapper.mapAppUserEntityToAppUserDto(appUserBeingUpdated);
    }

    private Set<Commute> updateCommutes(UpdateAppUserDto updateAppUserDto, AppUser appUserBeingUpdated){
        final Set<Commute> updatedCommuteEntities = new HashSet<>();
        if (updateAppUserDto.getAppUserCommuteDtos() != null) {
            for (CommuteDto commuteDto : updateAppUserDto.getAppUserCommuteDtos()) {
                final Commute updatedCommuteEntity = commuteMapper
                        .mapCommuteDtoToCommuteEntities(commuteDto, appUserBeingUpdated);
                updatedCommuteEntities.add(updatedCommuteEntity);
            }
        }
        return updatedCommuteEntities;
    }
}

