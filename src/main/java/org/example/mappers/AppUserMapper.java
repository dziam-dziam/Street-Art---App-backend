package org.example.mappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.dtos.commute.CommuteDto;
import org.example.dtos.user.AppUserDto;
import org.example.entities.AppUser;
import org.example.entities.City;
import org.example.entities.Commute;
import org.example.entities.District;
import org.example.exceptions.CityNotFoundException;
import org.example.exceptions.DistrictNotFoundByNameException;
import org.example.repositories.CityRepository;
import org.example.repositories.DistrictRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Builder
@AllArgsConstructor
public class AppUserMapper {
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final CommuteMapper commuteMapper;

    public AppUser mapAppUserDtoToAppUserEntity(AppUserDto appUserDto) {
        if (appUserDto == null) throw new IllegalArgumentException("AppUserDto is null");
        if (appUserDto.getAppUserCity() == null || appUserDto.getAppUserCity().isBlank())
            throw new IllegalArgumentException("AppUserDto city is null");
        if (appUserDto.getAppUserLiveInDistrict() == null || appUserDto.getAppUserLiveInDistrict().isBlank())
            throw new IllegalArgumentException("AppUserDto live-in district is null");

        City cityFromDto = cityRepository.findByCityName(appUserDto.getAppUserCity())
                .orElseThrow(() -> new CityNotFoundException(appUserDto.getAppUserCity()));
        District districtFromDto = districtRepository.findByDistrictName(appUserDto.getAppUserLiveInDistrict())
                .orElseThrow(() -> new DistrictNotFoundByNameException(appUserDto.getAppUserLiveInDistrict()));

        Hibernate.initialize(cityFromDto);

        return AppUser.builder()
                .appUserCity(cityFromDto)
                .appUserName(appUserDto.getAppUserName())
                .appUserEmail(appUserDto.getAppUserEmail())
                .appUserPassword(appUserDto.getAppUserPassword())
                .appUserNationality(appUserDto.getAppUserNationality())
                .appUserLanguagesSpoken(appUserDto.getAppUserLanguagesSpoken())
                .appUserLiveInDistrict(districtFromDto)
                .appUserCommutes(new HashSet<>())
                .build();
    }

    public AppUserDto mapAppUserEntityToAppUserDto(AppUser appUserEntity) {
        String appUserEntityCityName = getAppUserEntityCityName(appUserEntity);
        District appUserEntityLiveInDistrict = appUserEntity.getAppUserLiveInDistrict();
        String appUserEntityLiveInDistrictName = appUserEntityLiveInDistrict.getDistrictName();

        Set<Commute> appUserEntityCommutes = appUserEntity.getAppUserCommutes();
        Set<CommuteDto> appUserEntityCommuteDtos = new HashSet<>();

        for (Commute commuteEntity : appUserEntityCommutes) {
            CommuteDto commuteDto = commuteMapper.mapCommuteEntityToCommuteDto(commuteEntity);
            appUserEntityCommuteDtos.add(commuteDto);
        }

        return AppUserDto.builder()
                .appUserName(appUserEntity.getAppUserName())
                .appUserEmail(appUserEntity.getAppUserEmail())
                .appUserPassword(appUserEntity.getAppUserPassword())
                .appUserCity(appUserEntityCityName)
                .appUserNationality(appUserEntity.getAppUserNationality())
                .appUserLanguagesSpoken(appUserEntity.getAppUserLanguagesSpoken())
                .appUserLiveInDistrict(appUserEntityLiveInDistrictName)
                .appUserCommuteDtos(appUserEntityCommuteDtos)
                .build();
    }

    private static String getAppUserEntityCityName(AppUser appUserEntity) {
        if (appUserEntity == null) throw new IllegalArgumentException("AppUserEntity is null");
        if (appUserEntity.getAppUserCity() == null) throw new IllegalArgumentException("AppUserEntity city is null");
        if (appUserEntity.getAppUserLiveInDistrict() == null)
            throw new IllegalArgumentException("AppUserEntity live-in district district is null");

        City appUserEntityCity = appUserEntity.getAppUserCity();
        return appUserEntityCity.getCityName();
    }
}
