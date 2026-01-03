package org.example.dtos.photo;

import lombok.*;
import org.example.entities.ArtPiece;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDto {
    private String photoUrl;

    private ArtPiece artPieceOnPhoto;
}
