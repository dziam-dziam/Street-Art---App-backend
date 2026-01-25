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

    //TODO czy nie lepiej dawac info na frontend ze zostalo to usuniete pomyslnie lub nie? gdyz to void, ResponseEntity?
    //TODO oczywiscie przy zalozeniu ze uzytkownik usuwac recznie cos, to na 100% on musi wiedziec rezultat

    @DeleteMapping("artPiece/{id}")
    public void removeArtPieceById(@PathVariable Long id){
        removeServices.removeArtPieceById(id);
    }

    @DeleteMapping("appUser/{id}")
    public void removeAppUserById(@PathVariable Long id){
        removeServices.removeAppUserById(id);
    }

    @DeleteMapping("cities/{id}")
    public void removeCityById(@PathVariable Long id){
        removeServices.removeCityById(id);
    }

    @DeleteMapping("district/{id}")
    public void removeDistrictById(@PathVariable Long id){
        removeServices.removeDistrictById(id);
    }

}
