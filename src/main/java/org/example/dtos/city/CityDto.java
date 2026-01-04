package org.example.dtos.city;

import lombok.*;
import org.example.dtos.district.DistrictDto;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDto {
    private String cityName;

    private Long cityResidentsCount;

    private Set<DistrictDto> cityDistricts = new HashSet<>();

}
