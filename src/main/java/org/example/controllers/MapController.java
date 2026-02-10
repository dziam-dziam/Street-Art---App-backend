package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDetailsDto;
import org.example.dtos.artpiece.ArtPieceMapPointDto;
import org.example.enums.ArtPieceStyles;
import org.example.enums.ArtPieceTypes;
import org.example.services.get_services.GetArtPieceDetailsService;
import org.example.services.get_services.GetFilteredMapPointsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("map")
public class MapController {

    private final GetArtPieceDetailsService detailsService;
    private final GetFilteredMapPointsService filteredMapPointsService;

    @GetMapping("/artPieces")
    public List<ArtPieceMapPointDto> getArtPiecesForMap(
            @RequestParam(required = false) String district,
            @RequestParam(required = false) ArtPieceTypes artPieceType,
            @RequestParam(required = false) ArtPieceStyles artPieceStyle
            ) {
        return filteredMapPointsService.getMapPoints(district,artPieceStyle,artPieceType);
    }

    @GetMapping("/artPieces/{id}")
    public ArtPieceDetailsDto getArtPieceDetails(@PathVariable Long id) {
        return detailsService.getDetails(id);
    }


}
