package org.example.controllers.add_controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.AddArtPieceDto;
import org.example.services.add_and_register_services.AddArtPieceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("addNew")
public class AddControllers {

    private final AddArtPieceService addArtPieceService;

    @PostMapping("/addArtPiece")
    public ArtPieceDto addArtPiece(@Valid @RequestBody AddArtPieceDto addArtPieceDto) {
        return addArtPieceService.createArtPiece(addArtPieceDto);
    }


}
