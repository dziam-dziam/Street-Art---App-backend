package org.example.dtos.artpiece;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.ArtPieceStyles;
import org.example.enums.ArtPieceTypes;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddArtPieceDto {
    private String artPieceAddress;

    private String artPieceName;

    private boolean artPieceContainsText;

    private String artPiecePosition;

    private String artPieceUserDescription;

    private String artPieceDistrict;

    private String artPieceCity;

    private Set<ArtPieceTypes> artPieceTypes;

    private Set<ArtPieceStyles> artPieceStyles;

    private Set<String> artPieceTextLanguages;

    private Set<String> artPiecePhotoUrls;
}
