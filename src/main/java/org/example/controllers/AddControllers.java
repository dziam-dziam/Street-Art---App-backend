package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.dtos.artpiece.AddArtPieceDto;
import org.example.services.add_and_register_services.AddArtPieceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("addNew")
public class AddControllers {

    private final AddArtPieceService addArtPieceService;

    @PostMapping("/addArtPiece")
    public ArtPieceDto addArtPiece(@RequestBody AddArtPieceDto addArtPieceDto) {
        return addArtPieceService.createArtPiece(addArtPieceDto);
    }

}
