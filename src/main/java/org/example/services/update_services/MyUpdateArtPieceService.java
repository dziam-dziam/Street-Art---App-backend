package org.example.services.update_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.UpdateArtPieceDto;
import org.example.entities.AppUser;
import org.example.entities.ArtPiece;
import org.example.entities.District;
import org.example.entities.Location;
import org.example.exceptions.AppUserNotFoundByEmailException;
import org.example.exceptions.DistrictNotFoundByNameException;
import org.example.mappers.ArtPieceMapper;
import org.example.mappers.LocationMapper;
import org.example.repositories.AppUserRepository;
import org.example.repositories.ArtPieceRepository;
import org.example.repositories.DistrictRepository;
import org.example.repositories.LocationRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyUpdateArtPieceService {

    private final ArtPieceRepository artPieceRepository;
    private final AppUserRepository appUserRepository;

    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;
    private final DistrictRepository districtRepository;
    private final ArtPieceMapper artPieceMapper;

    @Transactional
    public ArtPieceDto updateMyArtPiece(Long artPieceId, UpdateArtPieceDto dto, Authentication auth) {
        String email = auth.getName();

        AppUser user = appUserRepository.findByAppUserEmail(email)
                .orElseThrow(() -> new AppUserNotFoundByEmailException(email));

        ArtPiece ap = artPieceRepository.findByIdAndArtPieceAppUserWhoAddedIt_Id(artPieceId, user.getId())
                .orElseThrow(() -> new AccessDeniedException("You can edit only your own ArtPieces."));

        final String oldAddress = ap.getArtPieceAddress();
        final String oldCity = ap.getArtPieceDistrict().getDistrictCity().getCityName();

        if (dto.getArtPieceDistrict() != null && !dto.getArtPieceDistrict().isBlank()) {
            final District district = districtRepository.findByDistrictName(dto.getArtPieceDistrict())
                    .orElseThrow(() -> new DistrictNotFoundByNameException(dto.getArtPieceDistrict()));
            ap.setArtPieceDistrict(district);

            if (ap.getArtPieceLocation() != null) {
                ap.getArtPieceLocation().setLocationDistrict(district);
            }
        }

        if (dto.getArtPieceAddress() != null) ap.setArtPieceAddress(dto.getArtPieceAddress());
        if (dto.getArtPieceName() != null) ap.setArtPieceName(dto.getArtPieceName());
        if (dto.getArtPieceUserDescription() != null) ap.setArtPieceUserDescription(dto.getArtPieceUserDescription());
        if (dto.getArtPiecePosition() != null) ap.setArtPiecePosition(dto.getArtPiecePosition());
        if (dto.getArtPieceContainsText() != null) ap.setArtPieceContainsText(dto.getArtPieceContainsText());
        if (dto.getArtPieceTypes() != null) ap.setArtPieceTypes(dto.getArtPieceTypes());
        if (dto.getArtPieceStyles() != null) ap.setArtPieceStyles(dto.getArtPieceStyles());
        if (dto.getArtPieceTextLanguages() != null) ap.setArtPieceTextLanguages(dto.getArtPieceTextLanguages());

        if (Boolean.FALSE.equals(ap.getArtPieceContainsText())) {
            ap.setArtPieceTextLanguages(null);
        }

        final String effectiveCity =
                (dto.getArtPieceCity() != null && !dto.getArtPieceCity().isBlank())
                        ? dto.getArtPieceCity()
                        : ap.getArtPieceDistrict().getDistrictCity().getCityName();

        final String effectiveAddress = ap.getArtPieceAddress();

        boolean addressChanged = (dto.getArtPieceAddress() != null) && !dto.getArtPieceAddress().equals(oldAddress);
        boolean cityChanged = (dto.getArtPieceCity() != null) && !dto.getArtPieceCity().equals(oldCity);

        if (addressChanged || cityChanged) {
            Location loc = locationMapper.mapAddressToLocationEntity(effectiveAddress, effectiveCity);

            if (loc.getId() == null) {
                loc = locationRepository.save(loc);
            }

            ap.setArtPieceLocation(loc);
            loc.setLocationDistrict(ap.getArtPieceDistrict());
        }

        return artPieceMapper.mapArtPieceEntityToArtPieceDto(artPieceRepository.save(ap));
    }
}
