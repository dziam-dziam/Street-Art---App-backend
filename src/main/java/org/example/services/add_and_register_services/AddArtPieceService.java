package org.example.services.add_and_register_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.AddArtPieceDto;
import org.example.entities.ArtPiece;
import org.example.entities.District;
import org.example.entities.Location;
import org.example.exceptions.DistrictNotFoundByNameException;
import org.example.exceptions.LocationAlreadyOccupiedException;
import org.example.mappers.ArtPieceMapper;
import org.example.mappers.LocationMapper;
import org.example.repositories.ArtPieceRepository;
import org.example.repositories.DistrictRepository;
import org.example.repositories.LocationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddArtPieceService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final ArtPieceRepository artPieceRepository;
    private final ArtPieceMapper artPieceMapper;
    private final DistrictRepository districtRepository;

    public ArtPieceDto createArtPiece(AddArtPieceDto addArtPieceDto) {
        if (addArtPieceDto == null) throw new IllegalArgumentException("AddArtPieceDto is null");
        if (addArtPieceDto.getArtPieceAddress() == null)
            throw new IllegalArgumentException("AddArtPieceDto address is null");

        String artPieceDtoAddress = addArtPieceDto.getArtPieceAddress();

        String addArtPieceDtoCityName = addArtPieceDto.getArtPieceCity();
        Location artPieceLocation = locationMapper.mapAddressToLocationEntity(artPieceDtoAddress, addArtPieceDtoCityName);

        if (artPieceLocation.getId() == null) {
            artPieceLocation = locationRepository.save(artPieceLocation);
        } else throw new LocationAlreadyOccupiedException(artPieceRepository.getArtPiecesByLocationLatitudeAndLongitude
                (artPieceLocation.getLocationLatitude(), artPieceLocation.getLocationLongitude()), artPieceLocation);

        String artPieceDtoDistrictName = addArtPieceDto.getArtPieceDistrict();
        District artPieceDtoDistrictEntity = districtRepository.findByDistrictName(artPieceDtoDistrictName)
                .orElseThrow(() -> new DistrictNotFoundByNameException(artPieceDtoDistrictName));

        ArtPieceDto artPieceDto = ArtPieceDto.builder()
                .artPieceDistrict(artPieceDtoDistrictName)
                .artPieceAddress(artPieceDtoAddress)
                .artPieceName(addArtPieceDto.getArtPieceName())
                .artPieceStyles(addArtPieceDto.getArtPieceStyles())
                .artPieceTypes(addArtPieceDto.getArtPieceTypes())
                .artPieceContainsText(addArtPieceDto.isArtPieceContainsText())
                .artPiecePosition(addArtPieceDto.getArtPiecePosition())
                .artPieceCity(addArtPieceDtoCityName)
                .artPieceTextLanguages(addArtPieceDto.getArtPieceTextLanguages())
                .artPieceUserDescription(addArtPieceDto.getArtPieceUserDescription())
                .artPiecePhotoUrls(addArtPieceDto.getArtPiecePhotoUrls())
                .build();

        ArtPiece artPiece = artPieceMapper.mapArtPieceDtoToArtPieceEntity(artPieceDto);
        artPiece.setArtPieceLocation(artPieceLocation);

        artPieceDtoDistrictEntity.addArtPiece(artPiece);

        ArtPiece saved = artPieceRepository.save(artPiece);

        return artPieceMapper.mapArtPieceEntityToArtPieceDto(saved);
    }
}
