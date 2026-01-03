package org.example.controllers.auth;

import lombok.RequiredArgsConstructor;
import org.example.dtos.city.AddCityDto;
import org.example.dtos.city.CityDto;
import org.example.dtos.district.AddDistrictDto;
import org.example.dtos.district.DistrictDto;
import org.example.services.add_services.AddCityService;
import org.example.services.add_services.AddDistrictService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthStartingSetupController {

    private final AddCityService addCityService;
    private final AddDistrictService addDistrictService;

    @PostMapping("/addCity")
    public CityDto addCity(@RequestBody AddCityDto addCityDto) {
        return addCityService.createCity(addCityDto);
    }

    @PostMapping("/addDistrict")
    public DistrictDto addDistrict(@RequestBody AddDistrictDto addDistrictDto) {
        return addDistrictService.createDistrict(addDistrictDto);
    }
}
