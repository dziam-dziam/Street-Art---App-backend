package org.example.services.get_services.get_by_name_services;

import lombok.RequiredArgsConstructor;
import org.example.entities.District;
import org.example.exceptions.DistrictNotFoundByNameException;
import org.example.repositories.DistrictRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDistrictByNameService {

    private final DistrictRepository districtRepository;

    public District getDistrictByName(String districtName){
      return districtRepository.findByDistrictName(districtName)
              .orElseThrow(() -> new DistrictNotFoundByNameException("District with name: " + districtName + " was not found"));
    }
}
