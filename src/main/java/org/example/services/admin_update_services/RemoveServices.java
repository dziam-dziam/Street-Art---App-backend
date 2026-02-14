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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RemoveServices {

    private final AppUserRepository appUserRepository;
    private final ArtPieceRepository artPieceRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;

    private static final String ADMIN_MAIL = "damianzmudzinski3@gmail.com";

    public void removeAppUserById(Long appUserId) {
        final AppUser appUserToRemove = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new AppUserNotFoundByIdException(appUserId));

        if (appUserToRemove.getAppUserEmail() != null
                && ADMIN_MAIL.equalsIgnoreCase(appUserToRemove.getAppUserEmail().trim())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete protected admin user");
        }
        appUserRepository.delete(appUserToRemove);
    }

    public void removeArtPieceById(Long artPieceId) {
        final ArtPiece artPieceToRemove = artPieceRepository.findById(artPieceId)
                .orElseThrow(() -> new ArtPieceNotFoundByIdException(artPieceId));

        artPieceRepository.delete(artPieceToRemove);
    }

    public void removeCityById(Long cityId) {
        final City cityToRemove = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundByIdException(cityId));
        cityRepository.delete(cityToRemove);
    }

    public void removeDistrictById(Long districtId){
        final District districtToRemove = districtRepository.findById(districtId)
                .orElseThrow(() -> new DistrictNotFoundByIdException(districtId));
        districtRepository.delete(districtToRemove);
    }

}
