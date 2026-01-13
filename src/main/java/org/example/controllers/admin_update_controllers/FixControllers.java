package org.example.controllers.admin_update_controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.location.UpdateLocationDto;
import org.example.entities.Location;
import org.example.services.fix_admin_services.FixLocationService;
import org.example.services.fix_admin_services.RemovePhotoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("fix")
public class FixControllers {

    private final RemovePhotoService removePhotoService;
    private final FixLocationService fixLocationService;

    @DeleteMapping("photo/{id}")
    public void removeInvalidPhoto(@PathVariable Long id) {
        removePhotoService.removeInvalidPhotoById(id);
    }

    @PutMapping("location")
    public Location fixInvalidLocation(@RequestParam double currentLongitude, @RequestParam double currentLatitude, @RequestBody UpdateLocationDto updateLocationDto) {
        return fixLocationService.fixInvalidLocation(currentLongitude, currentLatitude, updateLocationDto);
    }
}
