package org.example.services.add_and_register_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.district.AddDistrictDto;
import org.example.dtos.district.DistrictDto;
import org.example.entities.District;
import org.example.mappers.DistrictMapper;
import org.example.repositories.DistrictRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterDistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

    public DistrictDto createDistrict(AddDistrictDto addDistrictDto) {
        if (addDistrictDto == null) throw new IllegalArgumentException("AddDistrictDto is null");

        String addDistrictDtoDistrictName = addDistrictDto.getDistrictName();

        if (districtRepository.findByDistrictName(addDistrictDtoDistrictName).isPresent()) {
            throw new IllegalArgumentException("District with name: " + addDistrictDtoDistrictName + " already exists");
        }

        DistrictDto districtDto = DistrictDto.builder()
                .districtName(addDistrictDtoDistrictName)
                .districtCity(addDistrictDto.getDistrictCity())
                .districtZipCode(addDistrictDto.getDistrictZipCode())
                .districtResidentsCount(addDistrictDto.getDistrictResidentsCount())
                .build();

        District district = districtMapper.mapDistrictDtoToDistrictEntity(districtDto);
        District saved = districtRepository.save(district);

        return districtMapper.mapDistrictEntityToDistrictDto(saved);
    }
}
