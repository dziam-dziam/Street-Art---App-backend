package org.example.services.get_all_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.district.DistrictDto;
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

    public List<DistrictDto> getAllDistrict() {
        List<DistrictDto> districtDtos = new ArrayList<>();
        List<District> districtEntities = districtRepository.findAll();
        for (District districtEntity : districtEntities) {
            DistrictDto districtDto = districtMapper.mapDistrictEntityToDistrictDto(districtEntity);
            districtDtos.add(districtDto);
        }
        return districtDtos;
    }
}
