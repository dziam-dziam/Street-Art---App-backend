package org.example.dtos.location;

import lombok.*;
import org.example.entities.ArtPiece;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private double locationLongitude;

    private double locationLatitude;

    private double locationHeight;

    private Set<ArtPiece> locationArtPieces;
}
