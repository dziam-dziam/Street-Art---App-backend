package org.example.controllers.get_controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ResponseArtPieceDto;
import org.example.services.get_services.get_districts_artpieces_services.GetJezyceArtPiecesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/getArtPieces")
public class GetDistrictArtPiecesController {

    private final GetJezyceArtPiecesService getJezyceArtPiecesService;
    @GetMapping("/Jezyce")
    public List<ResponseArtPieceDto> getJezyceArtPieces(){
        return getJezyceArtPiecesService.getJezyceArtpieces();
    }
}
