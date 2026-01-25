package org.example.services.admin_update_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.district.DistrictAdminDto;
import org.example.dtos.district.UpdateDistrictDto;
import org.example.entities.City;
import org.example.entities.District;
import org.example.exceptions.CityNotFoundByNameException;
import org.example.exceptions.DistrictNotFoundByIdException;
import org.example.mappers.DistrictMapper;
import org.example.repositories.CityRepository;
import org.example.repositories.DistrictRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateDistrictService {

    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;
    private final DistrictMapper districtMapper;

    //TODO finals
    public DistrictAdminDto updateDistrictById(Long districtId, UpdateDistrictDto updateDistrictDto) {
        District districtToBeUpdated = districtRepository.findById(districtId)
                .orElseThrow(() -> new DistrictNotFoundByIdException(districtId));
        String updateDistrictDtoCityName = updateDistrictDto.getDistrictCity();
        City updateDistrictDtoCityEntity = cityRepository.findByCityName(updateDistrictDtoCityName)
                .orElseThrow(() -> new CityNotFoundByNameException(updateDistrictDtoCityName));

        districtToBeUpdated.setDistrictName(updateDistrictDto.getDistrictName());
        districtToBeUpdated.setDistrictCity(updateDistrictDtoCityEntity);
        districtToBeUpdated.setDistrictZipCode(updateDistrictDto.getDistrictZipCode());
        districtToBeUpdated.setDistrictResidentsCount(updateDistrictDto.getDistrictResidentsCount());

        districtRepository.save(districtToBeUpdated);

        return districtMapper.mapDistrictEntityToAdminDto(districtToBeUpdated);
    }
}
