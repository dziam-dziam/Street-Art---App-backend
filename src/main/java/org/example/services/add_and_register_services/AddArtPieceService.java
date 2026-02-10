package org.example.services.add_and_register_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.AddArtPieceDto;
import org.example.entities.AppUser;
import org.example.entities.ArtPiece;
import org.example.entities.District;
import org.example.entities.Location;
import org.example.exceptions.DistrictNotFoundByNameException;
import org.example.exceptions.LocationAlreadyOccupiedException;
import org.example.mappers.ArtPieceMapper;
import org.example.mappers.LocationMapper;
import org.example.repositories.AppUserRepository;
import org.example.repositories.ArtPieceRepository;
import org.example.repositories.DistrictRepository;
import org.example.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AddArtPieceService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final ArtPieceRepository artPieceRepository;
    private final ArtPieceMapper artPieceMapper;
    private final DistrictRepository districtRepository;
    private final AppUserRepository appUserRepository;

    public ArtPieceDto createArtPiece(AddArtPieceDto addArtPieceDto, Principal principal) {
        if (principal == null || principal.getName() == null ){
            throw new IllegalStateException("User is not authenticated");
        }

        AppUser appUser = appUserRepository.findByAppUserEmail(principal.getName())
                .orElseThrow(() -> new IllegalStateException("Logged user not found in DB: " + principal.getName()));

        if (addArtPieceDto == null) throw new IllegalArgumentException("AddArtPieceDto is null");
        if (addArtPieceDto.getArtPieceAddress() == null)
            throw new IllegalArgumentException("AddArtPieceDto address is null");

        final String addArtPieceDtoCityName = addArtPieceDto.getArtPieceCity();
        Location artPieceLocation = locationMapper.mapAddressToLocationEntity(addArtPieceDto.getArtPieceAddress(), addArtPieceDtoCityName);

            artPieceLocation = locationRepository.save(artPieceLocation);

        final String artPieceDtoDistrictName = addArtPieceDto.getArtPieceDistrict();
        final District artPieceDtoDistrictEntity = districtRepository.findByDistrictName(artPieceDtoDistrictName)
                .orElseThrow(() -> new DistrictNotFoundByNameException(artPieceDtoDistrictName));

        ArtPieceDto artPieceDto = ArtPieceDto.builder()
                .artPieceDistrict(artPieceDtoDistrictName)
                .artPieceAddress(addArtPieceDto.getArtPieceAddress())
                .artPieceName(addArtPieceDto.getArtPieceName())
                .artPieceStyles(addArtPieceDto.getArtPieceStyles())
                .artPieceTypes(addArtPieceDto.getArtPieceTypes())
                .artPieceContainsText(addArtPieceDto.getArtPieceContainsText())
                .artPiecePosition(addArtPieceDto.getArtPiecePosition())
                .artPieceCity(addArtPieceDtoCityName)
                .artPieceTextLanguages(addArtPieceDto.getArtPieceTextLanguages())
                .artPieceUserDescription(addArtPieceDto.getArtPieceUserDescription())
                .build();

        final ArtPiece artPiece = artPieceMapper.mapArtPieceDtoToArtPieceEntity(artPieceDto);

        artPiece.setArtPieceAppUserWhoAddedIt(appUser);
        artPiece.setArtPieceLocation(artPieceLocation);

        artPieceLocation.addArtPiece(artPiece);
        artPieceLocation.setLocationDistrict(artPieceDtoDistrictEntity);
        artPieceDtoDistrictEntity.addArtPiece(artPiece);

        final ArtPiece saved = artPieceRepository.save(artPiece);

        return artPieceMapper.mapArtPieceEntityToArtPieceDto(saved);
    }
}
