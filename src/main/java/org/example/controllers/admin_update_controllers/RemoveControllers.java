package org.example.controllers.admin_update_controllers;

import lombok.RequiredArgsConstructor;
import org.example.services.admin_update_services.RemoveServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("remove")
public class RemoveControllers {

    private final RemoveServices removeServices;

    @DeleteMapping("artPiece/{id}")
    public void removeArtPieceById(@PathVariable Long id) {
        removeServices.removeArtPieceById(id);
    }

    @DeleteMapping("appUser/{id}")
    public void removeAppUserById(@PathVariable Long id) {
        removeServices.removeAppUserById(id);
    }

    @DeleteMapping("cities/{id}")
    public void removeCityById(@PathVariable Long id) {
        removeServices.removeCityById(id);
    }

    @DeleteMapping("district/{id}")
    public void removeDistrictById(@PathVariable Long id) {
        removeServices.removeDistrictById(id);
    }

}
