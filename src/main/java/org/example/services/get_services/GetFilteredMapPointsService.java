package org.example.services.get_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceMapPointDto;
import org.example.entities.ArtPiece;
import org.example.entities.Location;
import org.example.enums.ArtPieceStyles;
import org.example.enums.ArtPieceTypes;
import org.example.repositories.ArtPieceRepository;
import org.example.services.ArtPieceSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFilteredMapPointsService {

    private final ArtPieceRepository artPieceRepository;

    public List<ArtPieceMapPointDto> getMapPoints(String district,
                                                  ArtPieceStyles artPieceStyle,
                                                  ArtPieceTypes artPieceType) {
        Specification<ArtPiece> specification = Specification
                .where(ArtPieceSpecification.withLocationFetched())
                .and(ArtPieceSpecification.hasLocation())
                .and(ArtPieceSpecification.districtEquals(district))
                .and(ArtPieceSpecification.styleMember(artPieceStyle))
                .and(ArtPieceSpecification.typeMember(artPieceType));

        return artPieceRepository.findAll(specification).stream()
                .map(this::toMapPointDto)
                .toList();
    }

    private ArtPieceMapPointDto toMapPointDto(ArtPiece artPiece){
        Location location = artPiece.getArtPieceLocation();
        return ArtPieceMapPointDto.builder()
                .id(artPiece.getId())
                .title(artPiece.getArtPieceName())
                .address(artPiece.getArtPieceAddress())
                .district(artPiece.getArtPieceDistrict() != null
                        ? artPiece.getArtPieceDistrict().getDistrictName()
                        : null)
                .lat(location.getLocationLatitude())
                .lng(location.getLocationLongitude())
                .build();
    }


}
