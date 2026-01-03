package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.city.CityDto;
import org.example.dtos.district.DistrictDto;
import org.example.dtos.user.AppUserDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("getAll")
public class GetAllControllers {
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final ArtPieceRepository artPieceRepository;
    private final AppUserRepository appUserRepository;
    private final CityMapper cityMapper;
    private final DistrictMapper districtMapper;
    private final ArtPieceMapper artPieceMapper;
    private final AppUserMapper appUserMapper;

    @GetMapping("/cities")
    public List<CityDto> getAllCities() {
        List<CityDto> cityDtos = new ArrayList<>();
        List<City> cityEntities = cityRepository.findAll();
        for (City cityEntity : cityEntities) {
            CityDto cityDto = cityMapper.mapCityEntityToCityDto(cityEntity);
            cityDtos.add(cityDto);
        }
        return cityDtos;
    }

    @GetMapping("/districts")
    public List<DistrictDto> getAllDistrict() {
        List<DistrictDto> districtDtos = new ArrayList<>();
        List<District> districtEntities = districtRepository.findAll();
        for (District districtEntity : districtEntities) {
            DistrictDto districtDto = districtMapper.mapDistrictEntityToDistrictDto(districtEntity);
            districtDtos.add(districtDto);
        }
        return districtDtos;
    }

    @GetMapping("/artPieces")
    public List<ArtPieceDto> getAllArtPieces() {
        List<ArtPieceDto> artPieceDtos = new ArrayList<>();
        List<ArtPiece> artPieceEntities = artPieceRepository.findAll();
        for (ArtPiece artPieceEntity : artPieceEntities) {
            ArtPieceDto artPieceDto = artPieceMapper.mapArtPieceEntityToArtPieceDto(artPieceEntity);
            artPieceDtos.add(artPieceDto);
        }
        return artPieceDtos;
    }

    @GetMapping("/appUsers")
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
