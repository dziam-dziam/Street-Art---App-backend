package org.example.dtos.artpiece;

import lombok.*;
import org.example.dtos.photo.PhotoResponseDto;
import org.example.enums.ArtPieceStyles;
import org.example.enums.ArtPieceTypes;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtPieceAdminDto {
    private Long id;

    private String artPieceAddress;

    private String artPieceName;

    private String artPieceUserDescription;
}
