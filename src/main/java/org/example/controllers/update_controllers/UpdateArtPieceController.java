package org.example.controllers.update_controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.UpdateArtPieceDto;
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
}
