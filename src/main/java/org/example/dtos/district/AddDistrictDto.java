package org.example.dtos.district;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddDistrictDto {

    private String districtName;

    private String districtCity;

    private Long districtResidentCount;
}
