package org.example.mappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.dtos.artpiece.ArtPieceAdminDto;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.ResponseArtPieceDto;
import org.example.dtos.photo.PhotoResponseDto;
import org.example.entities.*;
import org.example.services.get_services.get_by_name_services.GetDistrictByNameService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Component
@AllArgsConstructor
public class ArtPieceMapper {
    private final GetDistrictByNameService getDistrictByNameService;
    private final LocationMapper locationMapper;

    public ArtPiece mapArtPieceDtoToArtPieceEntity(ArtPieceDto artPieceDto) {
        if (artPieceDto == null) throw new IllegalArgumentException("ArtPieceDto is null");
        if (artPieceDto.getArtPieceDistrict() == null)
            throw new IllegalArgumentException("ArtPieceDto district is null");

        String artPieceDtoDistrictName = artPieceDto.getArtPieceDistrict();
        District districtFromDto = getDistrictByNameService.getDistrictByName(artPieceDtoDistrictName);

        return ArtPiece.builder()
                .artPieceAddress(artPieceDto.getArtPieceAddress())
                .artPieceName(artPieceDto.getArtPieceName())
                .artPieceContainsText(artPieceDto.getArtPieceContainsText())
                .artPiecePosition(artPieceDto.getArtPiecePosition())
                .artPieceUserDescription(artPieceDto.getArtPieceUserDescription())
                .artPieceDistrict(districtFromDto)
                .artPieceStyles(artPieceDto.getArtPieceStyles())
                .artPieceTypes(artPieceDto.getArtPieceTypes())
                .artPieceTextLanguages(artPieceDto.getArtPieceTextLanguages())
                .build();
    }

    public ArtPieceDto mapArtPieceEntityToArtPieceDto(ArtPiece artPieceEntity) {
        if (artPieceEntity == null) throw new IllegalArgumentException("ArtPieceEntity is null");
        if (artPieceEntity.getArtPieceDistrict() == null)
            throw new IllegalArgumentException("ArtPieceEntity district is null");
        if (artPieceEntity.getArtPieceDistrict().getDistrictCity() == null)
            throw new IllegalArgumentException("ArtPieceEntity city retrieved from ArtPieceEntity district is null");

        District artPieceEntityDistrict = artPieceEntity.getArtPieceDistrict();
        City artPieceEntityDistrictCity = artPieceEntityDistrict.getDistrictCity();
        String artPieceEntityCityName = artPieceEntityDistrictCity.getCityName();
        String artPieceEntityDistrictName = artPieceEntityDistrict.getDistrictName();

        return ArtPieceDto.builder()
                .artPieceCity(artPieceEntityCityName)
                .artPieceAddress(artPieceEntity.getArtPieceAddress())
                .artPieceName(artPieceEntity.getArtPieceName())
                .artPieceContainsText(artPieceEntity.getArtPieceContainsText())
                .artPiecePosition(artPieceEntity.getArtPiecePosition())
                .artPieceDistrict(artPieceEntityDistrictName)
                .artPieceStyles(artPieceEntity.getArtPieceStyles())
                .artPieceTypes(artPieceEntity.getArtPieceTypes())
                .artPieceTextLanguages(artPieceEntity.getArtPieceTextLanguages())
                .artPiecePhotos(mapPhotoEntitiesToPhotoResponseDtos(artPieceEntity))
                .artPieceUserDescription(artPieceEntity.getArtPieceUserDescription())
                .build();

    }

    public ResponseArtPieceDto mapArtPieceEntityToResponseDto(ArtPiece artPieceEntity) {
        if (artPieceEntity == null)
            throw new IllegalArgumentException("ArtPieceEntity is null");
        if (artPieceEntity.getArtPieceDistrict() == null)
            throw new IllegalArgumentException("ArtPieceEntity district is null");
        if (artPieceEntity.getArtPieceDistrict().getDistrictCity() == null)
            throw new IllegalArgumentException("ArtPieceEntity city is null");


        District artPieceEntityDistrict = artPieceEntity.getArtPieceDistrict();
        City artPieceEntityDistrictCity = artPieceEntityDistrict.getDistrictCity();
        String artPieceEntityCityName = artPieceEntityDistrictCity.getCityName();
        String artPieceEntityDistrictName = artPieceEntityDistrict.getDistrictName();

        return ResponseArtPieceDto.builder()
                .artPieceAddress(artPieceEntity.getArtPieceAddress())
                .artPieceCity(artPieceEntityCityName)
                .artPieceDistrict(artPieceEntityDistrictName)
                .artPieceStyles(artPieceEntity.getArtPieceStyles())
                .artPieceName(artPieceEntity.getArtPieceName())
                .artPieceTypes(artPieceEntity.getArtPieceTypes())
                .artPiecePhotos(mapPhotoEntitiesToPhotoResponseDtos(artPieceEntity))
                .artPieceUserDescription(artPieceEntity.getArtPieceUserDescription())
                .build();
    }

    public ArtPieceAdminDto mapArtPieceEntityToAdminDto(ArtPiece artPieceEntity) {
        if (artPieceEntity == null) {
            throw new IllegalArgumentException("Artpiece entity is null");
        }

        return ArtPieceAdminDto.builder()
                .id(artPieceEntity.getId())
                .artPieceAddress(artPieceEntity.getArtPieceAddress())
                .artPieceName(artPieceEntity.getArtPieceName())
                .artPieceUserDescription(artPieceEntity.getArtPieceUserDescription())
                .build();
    }

    private static Set<PhotoResponseDto> mapPhotoEntitiesToPhotoResponseDtos(ArtPiece artPieceEntity) {
        Set<Photo> photos = artPieceEntity.getArtPiecePhotos();

        if (photos == null) return Collections.emptySet();

        return photos.stream()
                .map(p -> PhotoResponseDto.builder()
                        .fileName(p.getFileName())
                        .contentType(p.getContentType())
                        .sizeBytes(p.getSizeBytes())
                        .downloadUrl("/api/photos/" + p.getId())
                        .build())
                .collect(Collectors.toSet());
    }

}
