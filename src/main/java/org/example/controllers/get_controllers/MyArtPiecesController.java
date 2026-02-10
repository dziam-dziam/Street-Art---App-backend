package org.example.controllers.get_controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceMapPointDto;
import org.example.services.MyArtPiecesService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my")
public class MyArtPiecesController {

    private final MyArtPiecesService myArtPiecesService;

    @GetMapping("/artPieces")
    public List<ArtPieceMapPointDto> myArtPieces(Authentication auth) {
        return myArtPiecesService.getMyArtPieces(auth);
    }
}
