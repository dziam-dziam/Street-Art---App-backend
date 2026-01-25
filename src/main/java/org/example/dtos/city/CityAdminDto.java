package org.example.dtos.city;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityAdminDto {
    private Long id;

    private String cityName;
}
