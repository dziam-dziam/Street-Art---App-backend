package org.example.dtos.artpiece;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UpdateArtPieceDto {

    @NotBlank(message = "Address is required.")
    private String artPieceAddress;

    @NotBlank(message = "Name is required.")
    @Size(max = 50, message = "Name cannot exceed 50 characters.")
    private String artPieceName;

    @NotNull(message = "ContainsText is required.")
    private Boolean artPieceContainsText;

    @Size(max = 50, message = "Position cannot exceed 50 characters.")
    private String artPiecePosition;

    @Size(max = 200, message = "Description cannot exceed 200 characters.")
    private String artPieceUserDescription;

    private String artPieceDistrict;
    private String artPieceCity;

    @Size(min = 1, message = "Select at least one type.")
    private Set<ArtPieceTypes> artPieceTypes;

    @Size(min = 1, message = "Select at least one style.")
    private Set<ArtPieceStyles> artPieceStyles;

    private Set<@NotBlank(message = "Text language cannot be blank.") String> artPieceTextLanguages;

    @AssertTrue(message = "Select at least one text language.")
    public boolean isTextLanguagesValid() {
        if (Boolean.TRUE.equals(artPieceContainsText)) {
            return artPieceTextLanguages != null && !artPieceTextLanguages.isEmpty();
        }
        return true;
    }
}
