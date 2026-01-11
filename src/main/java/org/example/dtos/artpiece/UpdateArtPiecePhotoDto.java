package org.example.dtos.artpiece;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArtPiecePhotoDto {
    private Set<String> artPiecePhotoUrls;

}
