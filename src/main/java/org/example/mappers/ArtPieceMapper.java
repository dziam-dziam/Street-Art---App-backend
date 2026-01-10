package org.example.mappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.ResponseArtPieceDto;
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

        ArtPiece artPieceEntity = ArtPiece.builder()
                .artPieceAddress(artPieceDto.getArtPieceAddress())
                .artPieceName(artPieceDto.getArtPieceName())
                .artPieceContainsText(artPieceDto.isArtPieceContainsText())
                .artPiecePosition(artPieceDto.getArtPiecePosition())
                .artPieceUserDescription(artPieceDto.getArtPieceUserDescription())
                .artPieceDistrict(districtFromDto)
                .artPieceStyles(artPieceDto.getArtPieceStyles())
                .artPieceTypes(artPieceDto.getArtPieceTypes())
                .artPieceTextLanguages(artPieceDto.getArtPieceTextLanguages())
                .build();

        mapPhotoUrlsToPhotoEntities(artPieceDto, artPieceEntity);
        return artPieceEntity;
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
        Set<String> artPiecePhotoUrls = mapPhotoEntitiesToUrls(artPieceEntity);


        return ArtPieceDto.builder()
                .artPieceCity(artPieceEntityCityName)
                .artPieceAddress(artPieceEntity.getArtPieceAddress())
                .artPieceName(artPieceEntity.getArtPieceName())
                .artPieceContainsText(artPieceEntity.isArtPieceContainsText())
                .artPiecePosition(artPieceEntity.getArtPiecePosition())
                .artPieceDistrict(artPieceEntityDistrictName)
                .artPieceStyles(artPieceEntity.getArtPieceStyles())
                .artPieceTypes(artPieceEntity.getArtPieceTypes())
                .artPieceTextLanguages(artPieceEntity.getArtPieceTextLanguages())
                .artPieceUserDescription(artPieceEntity.getArtPieceUserDescription())
                .artPiecePhotoUrls(artPiecePhotoUrls)
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
        Set<String> artPiecePhotoUrls = mapPhotoEntitiesToUrls(artPieceEntity);

        return ResponseArtPieceDto.builder()
                .artPieceAddress(artPieceEntity.getArtPieceAddress())
                .artPieceCity(artPieceEntityCityName)
                .artPieceDistrict(artPieceEntityDistrictName)
                .artPieceStyles(artPieceEntity.getArtPieceStyles())
                .artPieceName(artPieceEntity.getArtPieceName())
                .artPieceTypes(artPieceEntity.getArtPieceTypes())
                .artPieceUserDescription(artPieceEntity.getArtPieceUserDescription())
                .artPiecePhotoUrls(artPiecePhotoUrls)
                .build();
    }

    private static void mapPhotoUrlsToPhotoEntities(ArtPieceDto artPieceDto, ArtPiece artPieceEntity) {
        Set<String> artPiecePhotoUrls = artPieceDto.getArtPiecePhotoUrls();
        if (artPiecePhotoUrls != null) {
            artPiecePhotoUrls.forEach(artPieceEntity::addPhoto);
        }
    }

    private static Set<String> mapPhotoEntitiesToUrls(ArtPiece artPieceEntity) {
        Set<Photo> artPieceEntityPhotos = artPieceEntity.getArtPiecePhotos();

        return artPieceEntityPhotos
                == null ? Collections.emptySet() :
                artPieceEntityPhotos.stream().map(Photo::getPhotoUrl).collect(Collectors.toSet());
    }
}
