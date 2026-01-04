package org.example.controllers.auth;

import lombok.RequiredArgsConstructor;
import org.example.dtos.city.AddCityDto;
import org.example.dtos.city.CityDto;
import org.example.dtos.district.AddDistrictDto;
import org.example.dtos.district.DistrictDto;
import org.example.services.add_and_register_services.RegisterCityService;
import org.example.services.add_and_register_services.RegisterDistrictService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthStartingSetupController {

    private final RegisterCityService registerCityService;
    private final RegisterDistrictService registerDistrictService;

    @PostMapping("/registerCity")
    public CityDto registerCity(@RequestBody AddCityDto addCityDto) {
        return registerCityService.createCity(addCityDto);
    }

    @PostMapping("/registerDistrict")
    public DistrictDto registerDistrict(@RequestBody AddDistrictDto addDistrictDto) {
        return registerDistrictService.createDistrict(addDistrictDto);
    }
}
