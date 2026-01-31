package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDetailsDto;
import org.example.dtos.artpiece.ArtPieceMapPointDto;
import org.example.services.get_services.GetArtPieceDetailsService;
import org.example.services.get_services.GetArtPieceMapPointsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("map")
public class MapController {

    private final GetArtPieceMapPointsService service;
    private final GetArtPieceDetailsService detailsService;

    @GetMapping("/artPieces")
    public List<ArtPieceMapPointDto> getArtPiecesForMap() {
        return service.getAllMapPoints();
    }

    @GetMapping("/artPieces/{id}")
    public ArtPieceDetailsDto getArtPieceDetails(@PathVariable Long id) {
        return detailsService.getDetails(id);
    }

}
