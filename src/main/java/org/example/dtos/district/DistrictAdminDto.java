package org.example.dtos.district;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistrictAdminDto {
    private Long id;

    private String districtZipCode;

    private String districtName;

    private String districtCity;
}
