package org.example.dtos.artpiece;

import jakarta.validation.constraints.*;
import lombok.*;
import org.example.enums.ArtPieceStyles;
import org.example.enums.ArtPieceTypes;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddArtPieceDto {

    @NotBlank(message = "Address is required")
    private String artPieceAddress;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String artPieceName;

    @NotNull(message = "ContainsText is required")
    private Boolean artPieceContainsText;

    @Size(max = 50, message = "Position cannot exceed 50 characters")
    private String artPiecePosition;

    @Size(max = 200, message = "Description cannot exceed 200 characters")
    private String artPieceUserDescription;

    @NotBlank(message = "District is required")
    private String artPieceDistrict;

    @NotBlank(message = "City is required")
    private String artPieceCity;

    @NotEmpty(message = "Select at least one type")
    private Set<ArtPieceTypes> artPieceTypes;

    @NotEmpty(message = "Select at least one style")
    private Set<ArtPieceStyles> artPieceStyles;

    private Set<String> artPieceTextLanguages;

    @AssertTrue(message = "Select at least one text language when 'containsText' is true")
    public boolean isTextLanguagesValid() {
        if (!Boolean.TRUE.equals(artPieceContainsText)) return true;
        return artPieceTextLanguages != null && !artPieceTextLanguages.isEmpty();
    }
}
