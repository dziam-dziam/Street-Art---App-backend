package org.example.services.admin_update_services;

import lombok.RequiredArgsConstructor;
import org.example.entities.AppUser;
import org.example.entities.ArtPiece;
import org.example.exceptions.AppUserNotFoundByIdException;
import org.example.exceptions.ArtPieceNotFoundByIdException;
import org.example.repositories.AppUserRepository;
import org.example.repositories.ArtPieceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveServices {

    private final AppUserRepository appUserRepository;
    private final ArtPieceRepository artPieceRepository;

    public void removeAppUserById(Long appUserId){
        AppUser appUserToRemove = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new AppUserNotFoundByIdException(appUserId));

        appUserRepository.delete(appUserToRemove);
    }

    public void removeArtPieceById(Long artPieceId){
        ArtPiece artPieceToRemove = artPieceRepository.findById(artPieceId)
                .orElseThrow(() -> new ArtPieceNotFoundByIdException(artPieceId));

        artPieceRepository.delete(artPieceToRemove);
    }

}
