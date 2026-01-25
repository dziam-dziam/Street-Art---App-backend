package org.example.services.get_services.get_all_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.district.DistrictAdminDto;
import org.example.entities.District;
import org.example.mappers.DistrictMapper;
import org.example.repositories.DistrictRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllDistrictsService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

    //TODO finals
    public List<DistrictAdminDto> getAllDistrict() {
        List<DistrictAdminDto> districtDtos = new ArrayList<>();
        List<District> districtEntities = districtRepository.findAll();
        for (District districtEntity : districtEntities) {
            DistrictAdminDto districtDto = districtMapper.mapDistrictEntityToAdminDto(districtEntity);
            districtDtos.add(districtDto);
        }
        return districtDtos;
    }
}
