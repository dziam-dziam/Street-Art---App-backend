package org.example.services.update_services;

import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.UpdateArtPieceDto;
import org.example.entities.ArtPiece;
import org.example.entities.City;
import org.example.entities.District;
import org.example.entities.Location;
import org.example.exceptions.ArtPieceNotFoundByIdException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateArtPieceServiceTest {

    //TODO private pola!

    @Mock
    ArtPieceRepository artPieceRepository;

    @Mock
    LocationMapper locationMapper;

    @Mock
    LocationRepository locationRepository;

    @Mock
    DistrictRepository districtRepository;

    @Mock
    ArtPieceMapper artPieceMapper;

    @InjectMocks
    UpdateArtPieceService updateArtPieceService;

    @Test
    void should_update_artpiece_when_new_location_and_district_found_by_name() {
        //given
        Long artPieceToUpdateId = 1L;
        Long updatedDistrictId = 2L;
        Long updatedCityId = 3L;

        String updatedAddress = "new address 1";
        String updatedCityName = "Warszawa";
        String updatedDistrictName = "Jeżyce";

        UpdateArtPieceDto updateArtPieceDto = UpdateArtPieceDto.builder()
                .artPieceAddress(updatedAddress)
                .artPieceCity(updatedCityName)
                .artPieceDistrict(updatedDistrictName)
                .build();

        ArtPieceDto updatedArtPieceEntityMappedToartPieceDto = ArtPieceDto.builder().artPieceDistrict(updatedDistrictName).build();

        City updatedCity = City.builder()
                .id(updatedCityId)
                .cityName(updatedCityName)
                .build();

        District updatedDistrict = District.builder()
                .id(updatedDistrictId)
                .districtName(updatedDistrictName)
                .districtCity(updatedCity)
                .build();

        Location updatedLocation = Location.builder()
                .id(null)
                .locationLatitude(12)
                .locationLongitude(10)
                .build();

        Location savedUpdatedLocation = Location.builder()
                .id(10L)
                .locationLatitude(12)
                .locationLongitude(10)
                .build();

        ArtPiece artPieceToUpdate = ArtPiece.builder()
                .id(artPieceToUpdateId)
                .build();

        when(artPieceRepository.findById(artPieceToUpdateId)).
                thenReturn(Optional.of(artPieceToUpdate));

        when(locationMapper.mapAddressToLocationEntity(updatedAddress, updatedCityName)).
                thenReturn(updatedLocation);

        when(locationRepository.save(updatedLocation))
                .thenReturn(savedUpdatedLocation);

        when(districtRepository.findByDistrictName(updatedDistrictName))
                .thenReturn(Optional.of(updatedDistrict));

        when(artPieceRepository.save(artPieceToUpdate))
                .thenReturn(artPieceToUpdate);

        when(artPieceMapper.mapArtPieceEntityToArtPieceDto(artPieceToUpdate))
                .thenReturn(updatedArtPieceEntityMappedToartPieceDto);


        //when
        ArtPieceDto result = updateArtPieceService.updateArtPieceById(artPieceToUpdateId, updateArtPieceDto);

        //then
        assertEquals(updatedDistrict, artPieceToUpdate.getArtPieceDistrict());
        assertEquals(updatedCityName, artPieceToUpdate.getArtPieceDistrict().getDistrictCity().getCityName());
        assertEquals(updatedCity, artPieceToUpdate.getArtPieceDistrict().getDistrictCity());
        assertEquals(updatedAddress, artPieceToUpdate.getArtPieceAddress());
        assertEquals(savedUpdatedLocation, artPieceToUpdate.getArtPieceLocation());

        assertNotNull(result);
        assertEquals(updatedDistrictName, result.getArtPieceDistrict());

        verify(artPieceRepository).findById(artPieceToUpdateId);
        verify(locationMapper).mapAddressToLocationEntity(updatedAddress, updatedCityName);
        verify(locationRepository).save(updatedLocation);
        verify(districtRepository).findByDistrictName(updatedDistrictName);
        verify(artPieceRepository).save(artPieceToUpdate);
        verify(artPieceMapper).mapArtPieceEntityToArtPieceDto(artPieceToUpdate);

    }

    @Test
    void should_throw_when_artpiece_not_found() {
        Long wrongArtPieceToUpdateId = 1L;

        UpdateArtPieceDto updateArtPieceDto = UpdateArtPieceDto
                .builder().build();


        when(artPieceRepository.findById(wrongArtPieceToUpdateId)).thenReturn(Optional.empty());

        assertThrows(ArtPieceNotFoundByIdException.class, () -> updateArtPieceService.updateArtPieceById(wrongArtPieceToUpdateId, updateArtPieceDto));

        verify(artPieceRepository, never()).save(any());
        verify(artPieceMapper, never()).mapArtPieceEntityToArtPieceDto(any());
    }

    @Test
    void should_throw_when_return_existing_location() {
        String occupiedArtPieceAddress = "occupied address 1";
        String updatedArtPieceCityName = "Poznań";

        long artPieceToUpdateId = 2L;
        long occupiedLocationId = 1L;
        int occupiedLocationLongitude = 10;
        int occupiedLocationLatitude = 2;

        ArtPiece artPieceToUpdate = ArtPiece
                .builder()
                .id(artPieceToUpdateId)
                .build();

        Location occupiedLocation = Location.builder()
                .id(occupiedLocationId)
                .locationLongitude(occupiedLocationLongitude)
                .locationLatitude(occupiedLocationLatitude)
                .build();

        UpdateArtPieceDto updateArtPieceDto = UpdateArtPieceDto.builder()
                .artPieceAddress(occupiedArtPieceAddress)
                .artPieceCity(updatedArtPieceCityName)
                .build();

        when(artPieceRepository.findById(artPieceToUpdateId)).thenReturn(Optional.ofNullable(artPieceToUpdate));

        when(locationMapper.mapAddressToLocationEntity(occupiedArtPieceAddress, updatedArtPieceCityName))
                .thenReturn(occupiedLocation);

        assertThrows(LocationAlreadyOccupiedException.class, () -> updateArtPieceService.updateArtPieceById(artPieceToUpdateId, updateArtPieceDto));

        verify(locationRepository, never()).save(any());
        verify(artPieceRepository).findById(artPieceToUpdateId);
        verify(locationMapper).mapAddressToLocationEntity(occupiedArtPieceAddress,updatedArtPieceCityName);

    }

    @Test
    void should_throw_when_district_not_found() {
        long artPieceToBeUpdatedId = 1L;
        long mappedLocationLatitude = 10L;
        long mappedLocationLongitude = 2L;
        long savedMappedLocationId = 20L;

        String updatedAddress = "new address 1";
        String updatedCityName = "Poznań";
        String notFoundDistrictName = "not found";

        ArtPiece artPieceToBeUpdated = ArtPiece.builder()
                .id(artPieceToBeUpdatedId)
                .build();


        UpdateArtPieceDto updateArtPieceDto = UpdateArtPieceDto.builder()
                .artPieceAddress(updatedAddress)
                .artPieceCity(updatedCityName)
                .artPieceDistrict(notFoundDistrictName)
                .build();

        Location addressMappedToLocation = Location.builder()
                .locationLatitude(mappedLocationLatitude)
                .locationLongitude(mappedLocationLongitude)
                .build();

        Location savedAddressMappedToLocation = Location.builder()
                .id(savedMappedLocationId)
                .locationLatitude(mappedLocationLatitude)
                .locationLongitude(mappedLocationLongitude)
                .build();

        when(artPieceRepository.findById(artPieceToBeUpdatedId))
                .thenReturn(Optional.of(artPieceToBeUpdated));

        when(locationMapper.mapAddressToLocationEntity(updatedAddress, updatedCityName))
                .thenReturn(addressMappedToLocation);

        when(locationRepository.save(addressMappedToLocation))
                .thenReturn(savedAddressMappedToLocation);

        when(districtRepository.findByDistrictName(notFoundDistrictName))
                .thenReturn(Optional.empty());

        assertThrows(DistrictNotFoundByNameException.class,() -> updateArtPieceService.updateArtPieceById(artPieceToBeUpdatedId,updateArtPieceDto));

        verify(artPieceRepository).findById(artPieceToBeUpdatedId);
        verify(locationMapper).mapAddressToLocationEntity(updatedAddress, updatedCityName);
        verify(locationRepository).save(addressMappedToLocation);
        verify(districtRepository).findByDistrictName(notFoundDistrictName);
        verify(artPieceRepository,never()).save(any());
        verify(artPieceMapper, never()).mapArtPieceEntityToArtPieceDto(any());

    }

}