package org.example.controllers.admin_update_controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.city.CityAdminDto;
import org.example.dtos.city.UpdateCityDto;
import org.example.services.admin_update_services.UpdateCityService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("updateAdminCity")
public class UpdateCityController {

    private final UpdateCityService updateCityService;

    @PutMapping("city/{id}")
    public CityAdminDto updateCity(@PathVariable Long id, @RequestBody UpdateCityDto updateCityDto) {
        return updateCityService.updateCityById(id, updateCityDto);
    }
}
