package org.example.controllers.update_controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.UpdateArtPieceDto;
import org.example.dtos.artpiece.UpdateArtPiecePhotoDto;
import org.example.services.update_services.UpdateArtPieceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("updateArtPiece")
public class UpdateArtPieceController {
    private final UpdateArtPieceService updateArtPieceService;

    @PutMapping("artPiece/{id}")
    public ArtPieceDto updateArtPiece(@PathVariable Long id, @RequestBody UpdateArtPieceDto updateArtPieceDto) {
        return updateArtPieceService.updateArtPieceById(id, updateArtPieceDto);
    }

    @PutMapping("photoAdd/{id}")
    public ArtPieceDto updatePhotoAdd(@PathVariable Long id, @RequestBody UpdateArtPiecePhotoDto updateArtPiecePhotoDto) {
        return updateArtPieceService.updateAddArtPiecePhotos(id, updateArtPiecePhotoDto);
    }

    @PutMapping("photoRemove/{id}")
    public ArtPieceDto updatePhotoRemove(@PathVariable Long id, @RequestParam String photoUrl) {
        return updateArtPieceService.updateRemoveArtPiecePhotos(id, photoUrl);
    }


}
