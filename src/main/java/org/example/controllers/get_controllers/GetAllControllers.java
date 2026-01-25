package org.example.controllers.get_controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.app_user.AppUserAdminDto;
import org.example.dtos.artpiece.ArtPieceAdminDto;
import org.example.dtos.artpiece.ResponseArtPieceDto;
import org.example.dtos.city.CityDto;
import org.example.dtos.district.DistrictAdminDto;
import org.example.dtos.district.DistrictDto;
import org.example.dtos.app_user.AppUserDto;
import org.example.services.get_services.get_all_services.GetAllAppUsersService;
import org.example.services.get_services.get_all_services.GetAllArtPiecesService;
import org.example.services.get_services.get_all_services.GetAllCitiesService;
import org.example.services.get_services.get_all_services.GetAllDistrictsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("getAll")
public class GetAllControllers {

    private final GetAllCitiesService getAllCitiesService;
    private final GetAllDistrictsService getAllDistrictsService;
    private final GetAllArtPiecesService getAllArtPiecesService;
    private final GetAllAppUsersService getAllAppUsersService;

    @GetMapping("/cities")
    public List<CityDto> getAllCities() {
        return getAllCitiesService.getAllCities();
    }

    @GetMapping("/districts")
    public List<DistrictAdminDto> getAllDistrict() {
        return getAllDistrictsService.getAllDistrict();
    }


    @GetMapping("/artPieces")
    public List<ArtPieceAdminDto> getAllArtPieces() {
        return getAllArtPiecesService.getAllArtPieces();
    }

    @GetMapping("/appUsers")
    public List<AppUserAdminDto> getAllAppUsers() {
        return getAllAppUsersService.getAllAppUsers();
    }

}
