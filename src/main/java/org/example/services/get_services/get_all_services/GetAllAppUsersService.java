package org.example.services.get_services.get_all_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.user.AppUserDto;
import org.example.entities.AppUser;
import org.example.mappers.AppUserMapper;
import org.example.repositories.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllAppUsersService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;

    public List<AppUserDto> getAllAppUsers() {
        List<AppUserDto> appUserDtos = new ArrayList<>();
        List<AppUser> appUserEntities = appUserRepository.findAll();
        for (AppUser appUserEntity : appUserEntities) {
            AppUserDto appUserDto = appUserMapper.mapAppUserEntityToAppUserDto(appUserEntity);
            appUserDtos.add(appUserDto);
        }
        return appUserDtos;
    }
}
