package org.example.mappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.entities.ArtPiece;
import org.example.entities.City;
import org.example.entities.District;
import org.example.exceptions.DistrictNotFoundByNameException;
import org.example.repositories.AppUserRepository;
import org.example.repositories.DistrictRepository;
import org.springframework.stereotype.Component;

@Builder
@Component
@AllArgsConstructor
public class ArtPieceMapper {
    private final DistrictRepository districtRepository;
    private final AppUserRepository appUserRepository;

    public ArtPiece mapArtPieceDtoToArtPieceEntity(ArtPieceDto artPieceDto) {
        if (artPieceDto == null) throw new IllegalArgumentException("ArtPieceDto is null");
        if (artPieceDto.getArtPieceDistrict() == null)
            throw new IllegalArgumentException("ArtPieceDto district is null");

        String artPieceDtoDistrictName = artPieceDto.getArtPieceDistrict();
        District districtFromDto = districtRepository.findByDistrictName(artPieceDtoDistrictName)
                .orElseThrow(() -> new DistrictNotFoundByNameException(artPieceDtoDistrictName));

        return ArtPiece.builder()
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
                .artPieceContainsText(artPieceEntity.isArtPieceContainsText())
                .artPiecePosition(artPieceEntity.getArtPiecePosition())
                .artPieceUserDescription(artPieceEntity.getArtPieceUserDescription())
                .artPieceDistrict(artPieceEntityDistrictName)
                .artPieceStyles(artPieceEntity.getArtPieceStyles())
                .artPieceTypes(artPieceEntity.getArtPieceTypes())
                .artPieceTextLanguages(artPieceEntity.getArtPieceTextLanguages())
                .artPieceUserDescription(artPieceEntity.getArtPieceUserDescription())
                .build();
    }
}
