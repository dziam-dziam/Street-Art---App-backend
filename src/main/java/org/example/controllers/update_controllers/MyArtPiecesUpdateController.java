package org.example.controllers.update_controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.UpdateArtPieceDto;
import org.example.services.update_services.MyUpdateArtPieceService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my")
public class MyArtPiecesUpdateController {

    private final MyUpdateArtPieceService myUpdateArtPieceService;

    @PutMapping("/artPieces/{id}")
    public ArtPieceDto updateMyArtPiece(
            @PathVariable Long id,
            @Valid @RequestBody UpdateArtPieceDto dto,
            Authentication auth
    ) {
        return myUpdateArtPieceService.updateMyArtPiece(id, dto, auth);
    }
}
