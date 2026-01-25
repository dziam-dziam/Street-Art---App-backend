package org.example.services.get_services.get_all_services;


import lombok.RequiredArgsConstructor;
import org.example.dtos.app_user.AppUserAdminDto;
import org.example.dtos.artpiece.ArtPieceAdminDto;
import org.example.dtos.city.CityAdminDto;
import org.example.dtos.district.DistrictAdminDto;
import org.example.entities.AppUser;
import org.example.entities.ArtPiece;
import org.example.entities.City;
import org.example.entities.District;
import org.example.mappers.AppUserMapper;
import org.example.mappers.ArtPieceMapper;
import org.example.mappers.CityMapper;
import org.example.mappers.DistrictMapper;
import org.example.repositories.AppUserRepository;
import org.example.repositories.ArtPieceRepository;
import org.example.repositories.CityRepository;
import org.example.repositories.DistrictRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminGetAllServices {

    private final AppUserRepository appUserRepository;
    private final ArtPieceRepository artPieceRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;

    private final ArtPieceMapper artPieceMapper;
    private final AppUserMapper appUserMapper;
    private final DistrictMapper districtMapper;
    private final CityMapper cityMapper;

    public List<ArtPieceAdminDto> getAllArtPieces(){
        List<ArtPiece> artPieceEntities = artPieceRepository.findAll();
        List<ArtPieceAdminDto> adminDtos = new ArrayList<>();
        for (ArtPiece artPiece : artPieceEntities){
            adminDtos.add(artPieceMapper.mapArtPieceEntityToAdminDto(artPiece));
        }
        return adminDtos;
    }

    public List<AppUserAdminDto> getAllAppUsers(){
        List<AppUser> appUserEntities = appUserRepository.findAll();
        List<AppUserAdminDto> adminDtos = new ArrayList<>();
        for (AppUser appUser : appUserEntities){
            adminDtos.add(appUserMapper.mapAppUserEntityToAdminDto(appUser));
        }
        return adminDtos;
    }

    public List<DistrictAdminDto> getAllDistricts(){
        List<District> districtsEntities = districtRepository.findAll();
        List<DistrictAdminDto> adminDtos = new ArrayList<>();
        for (District district : districtsEntities){
            adminDtos.add(districtMapper.mapDistrictEntityToAdminDto(district));
        }
        return adminDtos;
    }

    public List<CityAdminDto> getAllCities(){
        List<City> cityEntities = cityRepository.findAll();
        List<CityAdminDto> adminDtos = new ArrayList<>();
        for (City city : cityEntities){
            adminDtos.add(cityMapper.mapCityEntityToAdminDto(city));
        }
        return adminDtos;
    }
}
