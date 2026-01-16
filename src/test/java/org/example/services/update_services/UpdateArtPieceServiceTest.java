package org.example.services.update_services;

import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.UpdateArtPieceDto;
import org.example.entities.ArtPiece;
import org.example.entities.City;
import org.example.entities.District;
import org.example.entities.Location;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateArtPieceServiceTest {

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
        assertEquals(updatedCityName,artPieceToUpdate.getArtPieceDistrict().getDistrictCity().getCityName());
        assertEquals(updatedCity,artPieceToUpdate.getArtPieceDistrict().getDistrictCity());
        assertEquals(updatedAddress,artPieceToUpdate.getArtPieceAddress());
        assertEquals(savedUpdatedLocation,artPieceToUpdate.getArtPieceLocation());

        assertNotNull(result);
        assertEquals(updatedDistrictName,result.getArtPieceDistrict());

        verify(artPieceRepository).findById(artPieceToUpdateId);
        verify(locationMapper).mapAddressToLocationEntity(updatedAddress,updatedCityName);
        verify(locationRepository).save(updatedLocation);
        verify(districtRepository).findByDistrictName(updatedDistrictName);
        verify(artPieceRepository).save(artPieceToUpdate);
        verify(artPieceMapper).mapArtPieceEntityToArtPieceDto(artPieceToUpdate);

    }

}