package org.example.dtos.district;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDto {
    private String districtName;

    private String districtCity;

    private Long districtArtPiecesCount;

    private Long districtResidentsCount;
}
