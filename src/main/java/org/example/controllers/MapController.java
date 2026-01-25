package org.example.controllers.get_controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceMapPointDto;
import org.example.services.get_services.GetArtPieceMapPointsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("map")
public class MapController {

    private final GetArtPieceMapPointsService service;

    @GetMapping("/artPieces")
    public List<ArtPieceMapPointDto> getArtPiecesForMap() {
        return service.getAllMapPoints();
    }
}
