package org.example.dtos.artpiece;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ArtPieceMapPointDto {
    private Long id;
    private String title;
    private String address;
    private String district; // String bo w UI masz DistrictName
    private double lat;
    private double lng;
}
