package org.example.controllers.admin_update_controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.district.DistrictDto;
import org.example.dtos.district.UpdateDistrictDto;
import org.example.services.admin_update_services.UpdateDistrictService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("updateAdminDistrict")
public class UpdateDistrictController {
    private final UpdateDistrictService updateDistrictService;

    @PutMapping("district/{id}")
    public DistrictDto updateDistrict(@PathVariable Long id, @RequestBody UpdateDistrictDto updateDistrictDto){
        return updateDistrictService.updateDistrictById(id,updateDistrictDto);
    }
}
