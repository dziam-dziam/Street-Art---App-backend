package org.example.services.add_and_register_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.location.LocationDto;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.AddArtPieceDto;
import org.example.entities.ArtPiece;
import org.example.mappers.ArtPieceMapper;
import org.example.mappers.LocationMapper;
import org.example.repositories.ArtPieceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddArtPieceService {

    private final LocationMapper locationMapper;
    private final ArtPieceRepository artPieceRepository;
    private final ArtPieceMapper artPieceMapper;

    public ArtPieceDto createArtPiece(AddArtPieceDto addArtPieceDto) {
        if (addArtPieceDto == null) throw new IllegalArgumentException("AddArtPieceDto is null");
        if (addArtPieceDto.getArtPieceAddress() == null)
            throw new IllegalArgumentException("AddArtPieceDto address is null");

        String addArtPieceDtoAddress = addArtPieceDto.getArtPieceAddress();
        LocationDto location = locationMapper.mapAddressToLocationDto(addArtPieceDtoAddress);

        ArtPieceDto artPieceDto = ArtPieceDto.builder()
                .artPieceAddress(addArtPieceDtoAddress)
                .artPieceName(addArtPieceDto.getArtPieceName())
                .artPieceStyles(addArtPieceDto.getArtPieceStyles())
                .artPieceTypes(addArtPieceDto.getArtPieceTypes())
                .artPieceContainsText(addArtPieceDto.isArtPieceContainsText())
                .artPiecePosition(addArtPieceDto.getArtPiecePosition())
                .artPieceCity(addArtPieceDto.getArtPieceCity())
                .artPieceDistrict(addArtPieceDto.getArtPieceDistrict())
                .artPieceLocation(location)
                .artPieceTextLanguages(addArtPieceDto.getArtPieceTextLanguages())
                .artPieceUserDescription(addArtPieceDto.getArtPieceUserDescription())
                .artPiecePhotoUrls(addArtPieceDto.getArtPiecePhotoUrls())
                .build();

        ArtPiece artPiece = artPieceMapper.mapArtPieceDtoToArtPieceEntity(artPieceDto);
        ArtPiece saved = artPieceRepository.save(artPiece);

        return artPieceMapper.mapArtPieceEntityToArtPieceDto(saved);
    }
}
