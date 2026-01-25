package org.example.dtos.artpiece;

import lombok.*;

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
