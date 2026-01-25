package org.example.services.update_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.UpdateArtPieceDto;
import org.example.entities.ArtPiece;
import org.example.entities.Location;
import org.example.exceptions.ArtPieceNotFoundByIdException;
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

    //TODO finals
    public ArtPieceDto updateArtPieceById(Long artPieceId, UpdateArtPieceDto updatedArtPieceDto) {
        ArtPiece artPieceToBeUpdated = artPieceRepository.findById(artPieceId)
                .orElseThrow(() -> new ArtPieceNotFoundByIdException(artPieceId));

        //TODO po co zmienne z geta? bez sensu
        String updatedArtPieceDtoAddress = updatedArtPieceDto.getArtPieceAddress();
        String updatedArtPieceDtoCityName = updatedArtPieceDto.getArtPieceCity();


        Location updatedArtPieceDtoLocation = locationMapper
                .mapAddressToLocationEntity(updatedArtPieceDtoAddress, updatedArtPieceDtoCityName);

        //TODO wynies do osobnej metody dobrze opisanej

            updatedArtPieceDtoLocation = locationRepository.save(updatedArtPieceDtoLocation);

//        String artPieceDtoDistrictName = updatedArtPieceDto.getArtPieceDistrict();
//        District updatedArtPieceDistrictEntity = districtRepository.findByDistrictName(artPieceDtoDistrictName)
//                .orElseThrow(() -> new DistrictNotFoundByNameException(artPieceDtoDistrictName));


        artPieceToBeUpdated.setArtPieceAddress(updatedArtPieceDtoAddress);
        artPieceToBeUpdated.setArtPieceName(updatedArtPieceDto.getArtPieceName());
        artPieceToBeUpdated.setArtPieceUserDescription(updatedArtPieceDto.getArtPieceUserDescription());
//        artPieceToBeUpdated.setArtPieceDistrict(updatedArtPieceDistrictEntity);
        artPieceToBeUpdated.setArtPieceTypes(updatedArtPieceDto.getArtPieceTypes());
        artPieceToBeUpdated.setArtPieceStyles(updatedArtPieceDto.getArtPieceStyles());
        artPieceToBeUpdated.setArtPieceTextLanguages(updatedArtPieceDto.getArtPieceTextLanguages());
        artPieceToBeUpdated.setArtPieceLocation(updatedArtPieceDtoLocation);

        artPieceRepository.save(artPieceToBeUpdated);

        return artPieceMapper.mapArtPieceEntityToArtPieceDto(artPieceToBeUpdated);
    }
}
