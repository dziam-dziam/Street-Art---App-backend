package org.example.controllers.get_controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.app_user.AppUserAdminDto;
import org.example.dtos.artpiece.ArtPieceAdminDto;
import org.example.dtos.city.CityAdminDto;
import org.example.dtos.district.DistrictAdminDto;
import org.example.services.get_services.get_all_services.AdminGetAllServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/getAll")
@RequiredArgsConstructor
public class AdminGetAllControllers {

    private final AdminGetAllServices adminGetAllServices;

    @GetMapping("/cities")
    public List<CityAdminDto> getAllCities() {
        return adminGetAllServices.getAllCities();
    }

    @GetMapping("/districts")
    public List<DistrictAdminDto> getAllDistrict() {
        return adminGetAllServices.getAllDistricts();
    }

    @GetMapping("/artPieces")
    public List<ArtPieceAdminDto> getAllArtPieces() {
        return adminGetAllServices.getAllArtPieces();
    }

    @GetMapping("/appUsers")
    public List<AppUserAdminDto> getAllAppUsers() {
        return adminGetAllServices.getAllAppUsers();
    }

}

