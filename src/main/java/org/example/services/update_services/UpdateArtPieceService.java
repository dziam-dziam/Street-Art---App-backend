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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateArtPieceService {

    private final ArtPieceRepository artPieceRepository;
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;
    private final DistrictRepository districtRepository;
    private final ArtPieceMapper artPieceMapper;

    @Transactional
    public ArtPieceDto updateArtPieceById(Long artPieceId, UpdateArtPieceDto dto) {
        final ArtPiece ap = artPieceRepository.findById(artPieceId)
                .orElseThrow(() -> new ArtPieceNotFoundByIdException(artPieceId));

        // zapamiętaj "old" do porównania (ważne!)
        final String oldAddress = ap.getArtPieceAddress();
        final String oldCity = ap.getArtPieceDistrict().getDistrictCity().getCityName();

        // 1) district (jeśli podano)
        if (dto.getArtPieceDistrict() != null && !dto.getArtPieceDistrict().isBlank()) {
            final District district = districtRepository.findByDistrictName(dto.getArtPieceDistrict())
                    .orElseThrow(() -> new DistrictNotFoundByNameException(dto.getArtPieceDistrict()));
            ap.setArtPieceDistrict(district);

            // jeśli Location trzyma district, uaktualnij spójnie
            if (ap.getArtPieceLocation() != null) {
                ap.getArtPieceLocation().setLocationDistrict(district);
            }
        }

        // 2) merge pól (tylko non-null)
        if (dto.getArtPieceAddress() != null) ap.setArtPieceAddress(dto.getArtPieceAddress());
        if (dto.getArtPieceName() != null) ap.setArtPieceName(dto.getArtPieceName());
        if (dto.getArtPieceUserDescription() != null) ap.setArtPieceUserDescription(dto.getArtPieceUserDescription());

        if (dto.getArtPiecePosition() != null) ap.setArtPiecePosition(dto.getArtPiecePosition());
        if (dto.getArtPieceContainsText() != null) ap.setArtPieceContainsText(dto.getArtPieceContainsText());

        if (dto.getArtPieceTypes() != null) ap.setArtPieceTypes(dto.getArtPieceTypes());
        if (dto.getArtPieceStyles() != null) ap.setArtPieceStyles(dto.getArtPieceStyles());
        if (dto.getArtPieceTextLanguages() != null) ap.setArtPieceTextLanguages(dto.getArtPieceTextLanguages());

        if (Boolean.FALSE.equals(ap.getArtPieceContainsText())) {
            ap.setArtPieceTextLanguages(null); // albo Collections.emptySet()
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

            // jeśli Location istnieje już w bazie -> loc ma id, wtedy nie zapisuj drugi raz
            if (loc.getId() == null) {
                loc = locationRepository.save(loc);
            }

            ap.setArtPieceLocation(loc);

            // opcjonalnie: jeśli trzymasz district w Location, ustaw:
            loc.setLocationDistrict(ap.getArtPieceDistrict());
        }

        ArtPiece saved = artPieceRepository.save(ap);
        return artPieceMapper.mapArtPieceEntityToArtPieceDto(saved);
    }
}
