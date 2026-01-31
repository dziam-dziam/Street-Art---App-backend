package org.example.dtos.artpiece;

import lombok.*;
import org.example.dtos.photo.PhotoResponseDto;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtPieceDetailsDto {
    private Long id;

    private String artPieceAddress;
    private String artPieceName;
    private Boolean artPieceContainsText;
    private String artPiecePosition;
    private String artPieceUserDescription;

    private String districtName;
    private String cityName;

    private Set<String> artPieceTextLanguages;
    private Set<String> artPieceTypes;
    private Set<String> artPieceStyles;

    private Set<PhotoResponseDto> photos;
}
