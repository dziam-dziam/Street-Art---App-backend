package org.example.services.get_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDetailsDto;
import org.example.dtos.photo.PhotoResponseDto;
import org.example.entities.ArtPiece;
import org.example.exceptions.ArtPieceNotFoundByIdException;
import org.example.repositories.ArtPieceRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetArtPieceDetailsService {
    private final ArtPieceRepository artPieceRepository;

    public ArtPieceDetailsDto getDetails(Long artPieceId) {
        ArtPiece artPiece = artPieceRepository.findDetailsById(artPieceId)
                .orElseThrow(() -> new ArtPieceNotFoundByIdException(artPieceId));

        Set<String> types = artPiece.getArtPieceTypes() == null ? Set.of()
                : artPiece.getArtPieceTypes().stream().map(Enum::name).collect(Collectors.toSet());

        Set<String> styles = artPiece.getArtPieceStyles() == null ? Set.of()
                : artPiece.getArtPieceStyles().stream().map(Enum::name).collect(Collectors.toSet());

        Set<PhotoResponseDto> photos = artPiece.getArtPiecePhotos() == null ? Set.of()
                : artPiece.getArtPiecePhotos().stream()
                .map(photo -> PhotoResponseDto.builder()
                        .id(photo.getId())
                        .fileName(photo.getFileName())
                        .contentType(photo.getContentType())
                        .sizeBytes(photo.getSizeBytes())
                        .downloadUrl("/api/photos/download/" + photo.getId())
                        .build())
                .collect(Collectors.toSet());

        String districtName = artPiece.getArtPieceDistrict() != null ? artPiece.getArtPieceDistrict().getDistrictName() : null;
        String cityName = (artPiece.getArtPieceDistrict() != null && artPiece.getArtPieceDistrict().getDistrictCity() != null)
                ? artPiece.getArtPieceDistrict().getDistrictCity().getCityName()
                : null;

        return ArtPieceDetailsDto.builder()
                .id(artPiece.getId())
                .artPieceAddress(artPiece.getArtPieceAddress())
                .artPieceName(artPiece.getArtPieceName())
                .artPieceContainsText(artPiece.getArtPieceContainsText())
                .artPiecePosition(artPiece.getArtPiecePosition())
                .artPieceUserDescription(artPiece.getArtPieceUserDescription())
                .districtName(districtName)
                .cityName(cityName)
                .artPieceTextLanguages(artPiece.getArtPieceTextLanguages() == null ? Set.of() : artPiece.getArtPieceTextLanguages())
                .artPieceTypes(types)
                .artPieceStyles(styles)
                .photos(photos)
                .build();
    }
}
