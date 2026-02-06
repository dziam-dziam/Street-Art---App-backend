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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public AppUserDto updateAppUserByEmail(UpdateAppUserDto dto, String email) {

        AppUser u = appUserRepository.findByAppUserEmail(email)
                .orElseThrow(() -> new AppUserNotFoundByEmailException(email));

        if (dto.getAppUserLiveInDistrict() != null && !dto.getAppUserLiveInDistrict().isBlank()) {
            District d = districtRepository.findByDistrictName(dto.getAppUserLiveInDistrict())
                    .orElseThrow(() -> new DistrictNotFoundByNameException(dto.getAppUserLiveInDistrict()));
            u.setAppUserLiveInDistrict(d);
        }

        if (dto.getAppUserName() != null && !dto.getAppUserName().isBlank()) {
            u.setAppUserName(dto.getAppUserName().trim());
        }

        if (dto.getAppUserEmail() != null && !dto.getAppUserEmail().isBlank()) {
            u.setAppUserEmail(dto.getAppUserEmail().trim());
        }

        if (dto.getAppUserLanguagesSpoken() != null) {
            u.setAppUserLanguagesSpoken(dto.getAppUserLanguagesSpoken());
        }

        if (dto.getAppUserPassword() != null && !dto.getAppUserPassword().isBlank()) {
            u.setAppUserPassword(passwordEncoder.encode(dto.getAppUserPassword()));
        }

        if (dto.getAppUserCommuteDtos() != null) {
            Set<Commute> updated = updateCommutes(dto, u);
            u.getAppUserCommutes().clear();
            for (Commute c : updated) u.addCommute(c);
        }

        AppUser saved = appUserRepository.save(u);
        return appUserMapper.mapAppUserEntityToAppUserDto(saved);
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

