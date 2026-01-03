package org.example.dtos.city;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCityDto {
    private String cityName;

    private Long cityResidentsCount;

}
