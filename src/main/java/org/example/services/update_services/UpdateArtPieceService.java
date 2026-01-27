package org.example.services.update_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.UpdateArtPieceDto;
import org.example.entities.ArtPiece;
import org.example.entities.District;
import org.example.entities.Location;
import org.example.exceptions.ArtPieceNotFoundByIdException;
import org.example.exceptions.DistrictNotFoundByNameException;
import org.example.mappers.ArtPieceMapper;
import org.example.mappers.LocationMapper;
import org.example.repositories.ArtPieceRepository;
import org.example.repositories.DistrictRepository;
import org.example.repositories.LocationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateArtPieceService {

    private final ArtPieceRepository artPieceRepository;
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;
    private final DistrictRepository districtRepository;
    private final ArtPieceMapper artPieceMapper;

    public ArtPieceDto updateArtPieceById(Long artPieceId, UpdateArtPieceDto updatedArtPieceDto) {
        final ArtPiece artPieceToBeUpdated = artPieceRepository.findById(artPieceId)
                .orElseThrow(() -> new ArtPieceNotFoundByIdException(artPieceId));

        Location updatedArtPieceDtoLocation = locationMapper
                .mapAddressToLocationEntity(updatedArtPieceDto.getArtPieceAddress(), updatedArtPieceDto.getArtPieceCity());

        updatedArtPieceDtoLocation = locationRepository.save(updatedArtPieceDtoLocation);

        if (updatedArtPieceDto.getArtPieceDistrict() != null) {
            final District updatedArtPieceDistrictEntity = districtRepository.findByDistrictName(updatedArtPieceDto.getArtPieceDistrict())
                    .orElseThrow(() -> new DistrictNotFoundByNameException(updatedArtPieceDto.getArtPieceDistrict()));
            artPieceToBeUpdated.setArtPieceDistrict(updatedArtPieceDistrictEntity);
        }

        artPieceToBeUpdated.setArtPieceAddress(updatedArtPieceDto.getArtPieceAddress());
        artPieceToBeUpdated.setArtPieceName(updatedArtPieceDto.getArtPieceName());
        artPieceToBeUpdated.setArtPieceUserDescription(updatedArtPieceDto.getArtPieceUserDescription());
        artPieceToBeUpdated.setArtPieceTypes(updatedArtPieceDto.getArtPieceTypes());
        artPieceToBeUpdated.setArtPieceStyles(updatedArtPieceDto.getArtPieceStyles());
        artPieceToBeUpdated.setArtPieceTextLanguages(updatedArtPieceDto.getArtPieceTextLanguages());
        artPieceToBeUpdated.setArtPieceLocation(updatedArtPieceDtoLocation);

        artPieceRepository.save(artPieceToBeUpdated);

        return artPieceMapper.mapArtPieceEntityToArtPieceDto(artPieceToBeUpdated);
    }
}
