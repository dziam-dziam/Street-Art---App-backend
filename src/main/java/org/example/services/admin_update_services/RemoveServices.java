package org.example.services.admin_update_services;

import lombok.RequiredArgsConstructor;
import org.example.entities.AppUser;
import org.example.entities.ArtPiece;
import org.example.entities.City;
import org.example.entities.District;
import org.example.exceptions.AppUserNotFoundByIdException;
import org.example.exceptions.ArtPieceNotFoundByIdException;
import org.example.exceptions.CityNotFoundByIdException;
import org.example.exceptions.DistrictNotFoundByIdException;
import org.example.repositories.AppUserRepository;
import org.example.repositories.ArtPieceRepository;
import org.example.repositories.CityRepository;
import org.example.repositories.DistrictRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveServices {

    private final AppUserRepository appUserRepository;
    private final ArtPieceRepository artPieceRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;

    //TODO finals
    public void removeAppUserById(Long appUserId) {
        AppUser appUserToRemove = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new AppUserNotFoundByIdException(appUserId));

        appUserRepository.delete(appUserToRemove);
    }

    public void removeArtPieceById(Long artPieceId) {
        ArtPiece artPieceToRemove = artPieceRepository.findById(artPieceId)
                .orElseThrow(() -> new ArtPieceNotFoundByIdException(artPieceId));

        artPieceRepository.delete(artPieceToRemove);
    }

    public void removeCityById(Long cityId) {
        City cityToRemove = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundByIdException(cityId));
        cityRepository.delete(cityToRemove);
    }

    public void removeDistrictById(Long districtId){
        District districtToRemove = districtRepository.findById(districtId)
                .orElseThrow(() -> new DistrictNotFoundByIdException(districtId));
        districtRepository.delete(districtToRemove);
    }

}
