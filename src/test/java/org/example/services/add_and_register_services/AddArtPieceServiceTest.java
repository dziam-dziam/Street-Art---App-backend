package org.example.services.add_and_register_services;

import org.example.dtos.artpiece.AddArtPieceDto;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.entities.ArtPiece;
import org.example.entities.District;
import org.example.entities.Location;
import org.example.enums.ArtPieceStyles;
import org.example.enums.ArtPieceTypes;
import org.example.exceptions.DistrictNotFoundByNameException;
import org.example.exceptions.LocationAlreadyOccupiedException;
import org.example.mappers.ArtPieceMapper;
import org.example.mappers.LocationMapper;
import org.example.repositories.ArtPieceRepository;
import org.example.repositories.DistrictRepository;
import org.example.repositories.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddArtPieceServiceTest {

    @Mock private LocationRepository locationRepository;
    @Mock private LocationMapper locationMapper;
    @Mock private ArtPieceRepository artPieceRepository;
    @Mock private ArtPieceMapper artPieceMapper;
    @Mock private DistrictRepository districtRepository;

    @InjectMocks private AddArtPieceService addArtPieceService;

    @Test
    void should_create_art_piece_and_save_location_when_new_location() {
        AddArtPieceDto dto = AddArtPieceDto.builder()
                .artPieceAddress("Some street 1")
                .artPieceCity("Poznań")
                .artPieceDistrict("Jeżyce")
                .artPieceName("Piece1")
                .artPieceTypes(Set.of(ArtPieceTypes.MURAL))
                .artPieceStyles(Set.of(ArtPieceStyles.SOCIAL_COMMENTARY))
                .artPieceTextLanguages(Set.of("PL"))
                .artPieceContainsText(true)
                .artPiecePosition("wall")
                .artPieceUserDescription("desc")
                .build();

        Location newLocation = Location.builder().id(null).locationLatitude(52.4).locationLongitude(16.9).build();
        Location savedLocation = Location.builder().id(10L).locationLatitude(52.4).locationLongitude(16.9).build();
        when(locationMapper.mapAddressToLocationEntity("Some street 1", "Poznań")).thenReturn(newLocation);
        when(locationRepository.save(newLocation)).thenReturn(savedLocation);

        District district = District.builder().id(5L).districtName("Jeżyce").build();
        when(districtRepository.findByDistrictName("Jeżyce")).thenReturn(Optional.of(district));

        ArtPiece entity = ArtPiece.builder().id(1L).artPieceName("Piece1").build();
        when(artPieceMapper.mapArtPieceDtoToArtPieceEntity(any(ArtPieceDto.class))).thenReturn(entity);
        when(artPieceRepository.save(entity)).thenReturn(entity);

        ArtPieceDto expected = ArtPieceDto.builder().artPieceName("Piece1").artPieceDistrict("Jeżyce").build();
        when(artPieceMapper.mapArtPieceEntityToArtPieceDto(entity)).thenReturn(expected);

        ArtPieceDto result = addArtPieceService.createArtPiece(dto);

        assertNotNull(result);
        assertEquals("Piece1", result.getArtPieceName());
        assertEquals("Jeżyce", result.getArtPieceDistrict());

        verify(locationRepository).save(newLocation);
        verify(districtRepository).findByDistrictName("Jeżyce");
        verify(artPieceRepository).save(entity);
    }

    @Test
    void should_throw_location_already_occupied_when_location_has_id() {
        AddArtPieceDto dto = AddArtPieceDto.builder()
                .artPieceAddress("Some street 1")
                .artPieceCity("Poznań")
                .artPieceDistrict("Jeżyce")
                .build();

        Location existingLocation = Location.builder().id(99L).locationLatitude(52.4).locationLongitude(16.9).build();
        when(locationMapper.mapAddressToLocationEntity("Some street 1", "Poznań")).thenReturn(existingLocation);
        when(artPieceRepository.getArtPiecesByLocationLatitudeAndLongitude(52.4, 16.9))
                .thenReturn(List.of(ArtPiece.builder().id(1L).build()));

        assertThrows(LocationAlreadyOccupiedException.class, () -> addArtPieceService.createArtPiece(dto));

        verify(locationRepository, never()).save(any());
        verify(artPieceRepository, never()).save(any());
    }

    @Test
    void should_throw_when_district_not_found() {
        AddArtPieceDto dto = AddArtPieceDto.builder()
                .artPieceAddress("Some street 1")
                .artPieceCity("Poznań")
                .artPieceDistrict("NoDistrict")
                .build();

        Location newLocation = Location.builder().id(null).locationLatitude(52.4).locationLongitude(16.9).build();
        when(locationMapper.mapAddressToLocationEntity("Some street 1", "Poznań")).thenReturn(newLocation);
        when(locationRepository.save(newLocation)).thenReturn(Location.builder().id(10L).locationLatitude(52.4).locationLongitude(16.9).build());

        when(districtRepository.findByDistrictName("NoDistrict")).thenReturn(Optional.empty());

        assertThrows(DistrictNotFoundByNameException.class, () -> addArtPieceService.createArtPiece(dto));

        verify(artPieceRepository, never()).save(any());
    }
}
