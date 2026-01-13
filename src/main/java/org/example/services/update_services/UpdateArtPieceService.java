package org.example.services.update_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.UpdateArtPieceDto;
import org.example.dtos.artpiece.UpdateArtPiecePhotoDto;
import org.example.entities.ArtPiece;
import org.example.entities.District;
import org.example.entities.Location;
import org.example.entities.Photo;
import org.example.exceptions.ArtPieceNotFoundException;
import org.example.exceptions.DistrictNotFoundByNameException;
import org.example.exceptions.LocationAlreadyOccupiedException;
import org.example.exceptions.PhotoNotFoundByUrlException;
import org.example.mappers.ArtPieceMapper;
import org.example.mappers.LocationMapper;
import org.example.repositories.ArtPieceRepository;
import org.example.repositories.DistrictRepository;
import org.example.repositories.LocationRepository;
import org.example.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UpdateArtPieceService {

    private final ArtPieceRepository artPieceRepository;
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;
    private final DistrictRepository districtRepository;
    private final ArtPieceMapper artPieceMapper;
    private final PhotoRepository photoRepository;

    public ArtPieceDto updateArtPieceById(Long artPieceId, UpdateArtPieceDto updatedArtPieceDto) {
        ArtPiece artPieceToBeUpdated = artPieceRepository.findById(artPieceId)
                .orElseThrow(() -> new ArtPieceNotFoundException(artPieceId));

        String updatedArtPieceDtoAddress = updatedArtPieceDto.getArtPieceAddress();

        String updatedArtPieceDtoCityName = updatedArtPieceDto.getArtPieceCity();

        Location artPieceLocation = locationMapper.mapAddressToLocationEntity(updatedArtPieceDtoAddress, updatedArtPieceDtoCityName);

        if (artPieceLocation.getId() == null) {
            artPieceLocation = locationRepository.save(artPieceLocation);
        } else throw new LocationAlreadyOccupiedException(artPieceRepository.getArtPiecesByLocationLatitudeAndLongitude
                (artPieceLocation.getLocationLatitude(), artPieceLocation.getLocationLongitude()), artPieceLocation);

        String artPieceDtoDistrictName = updatedArtPieceDto.getArtPieceDistrict();
        District updatedArtPieceDistrictEntity = districtRepository.findByDistrictName(artPieceDtoDistrictName)
                .orElseThrow(() -> new DistrictNotFoundByNameException(artPieceDtoDistrictName));


        artPieceToBeUpdated.setArtPieceAddress(updatedArtPieceDtoAddress);
        artPieceToBeUpdated.setArtPieceName(updatedArtPieceDto.getArtPieceName());
        artPieceToBeUpdated.setArtPieceContainsText(updatedArtPieceDto.isArtPieceContainsText());
        artPieceToBeUpdated.setArtPieceUserDescription(updatedArtPieceDto.getArtPieceUserDescription());
        artPieceToBeUpdated.setArtPieceDistrict(updatedArtPieceDistrictEntity);
        artPieceToBeUpdated.setArtPieceTypes(updatedArtPieceDto.getArtPieceTypes());
        artPieceToBeUpdated.setArtPieceStyles(updatedArtPieceDto.getArtPieceStyles());
        artPieceToBeUpdated.setArtPieceTextLanguages(updatedArtPieceDto.getArtPieceTextLanguages());
        artPieceToBeUpdated.setArtPieceLocation(artPieceLocation);

        artPieceRepository.save(artPieceToBeUpdated);

        return artPieceMapper.mapArtPieceEntityToArtPieceDto(artPieceToBeUpdated);
    }

    public ArtPieceDto updateAddArtPiecePhotos(Long artPieceId, UpdateArtPiecePhotoDto updateArtPiecePhotoDto) {
        ArtPiece artPieceToBeUpdated = artPieceRepository.findById(artPieceId)
                .orElseThrow(() -> new ArtPieceNotFoundException(artPieceId));

        Set<String> artPiecePhotoUrls = updateArtPiecePhotoDto.getArtPiecePhotoUrls();
        if (artPiecePhotoUrls != null) {
            artPiecePhotoUrls.forEach(artPieceToBeUpdated::addPhoto);
        }

        artPieceRepository.save(artPieceToBeUpdated);

        return artPieceMapper.mapArtPieceEntityToArtPieceDto(artPieceToBeUpdated);
    }

    public ArtPieceDto updateRemoveArtPiecePhotos(Long artPieceId,String photoUrl) {
        ArtPiece artPieceToBeUpdated = artPieceRepository.findById(artPieceId)
                .orElseThrow(() -> new ArtPieceNotFoundException(artPieceId));

        if (photoUrl == null) throw new IllegalArgumentException("Photo URL is null");

        Photo photoToRemove = photoRepository.findPhotoByUrl(photoUrl)
                .orElseThrow(() -> new PhotoNotFoundByUrlException(photoUrl));

        boolean removed = artPieceToBeUpdated.getArtPiecePhotos().remove(photoToRemove);

        if (removed) {
            photoToRemove.setArtPieceOnPhoto(null);
        }
        artPieceToBeUpdated.removePhoto(photoToRemove);

        artPieceRepository.save(artPieceToBeUpdated);

        return artPieceMapper.mapArtPieceEntityToArtPieceDto(artPieceToBeUpdated);
    }
}
