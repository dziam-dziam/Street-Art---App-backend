package org.example.dtos.artpiece;

import lombok.*;
import org.example.dtos.location.LocationDto;
import org.example.enums.ArtPieceStyles;
import org.example.enums.ArtPieceTypes;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtPieceDto {
    private String artPieceAddress;

    private String artPieceName;

    private boolean artPieceContainsText;

    private String artPiecePosition;

    private String artPieceUserDescription;

    private String artPieceDistrict;

    private String artPieceCity;

    private LocationDto artPieceLocation;

    private Set<ArtPieceTypes> artPieceTypes;

    private Set<ArtPieceStyles> artPieceStyles;

    private Set<String> artPieceTextLanguages;

    private Set<String> artPiecePhotoUrls = new HashSet<>();
}
