package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceMapPointDto;
import org.example.entities.AppUser;
import org.example.exceptions.AppUserNotFoundByEmailException;
import org.example.repositories.AppUserRepository;
import org.example.repositories.ArtPieceRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyArtPiecesService {
    private final ArtPieceRepository artPieceRepository;
    private final AppUserRepository appUserRepository;

    public List<ArtPieceMapPointDto> getMyArtPieces(Authentication auth) {
        String email = auth.getName();

        AppUser user = appUserRepository.findByAppUserEmail(email)
                .orElseThrow(() -> new AppUserNotFoundByEmailException(email));

        return artPieceRepository.findMyMapPoints(user.getId());
    }
}
