package org.example.dtos.district;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDistrictDto {
    private String districtZipCode;

    private String districtName;

    private String districtCity;

    private Long districtResidentsCount;
}
