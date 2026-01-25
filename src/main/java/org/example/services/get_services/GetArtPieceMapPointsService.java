package org.example.services.get_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceMapPointDto;
import org.example.repositories.ArtPieceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetArtPieceMapPointsService {

    private final ArtPieceRepository artPieceRepository;

    public List<ArtPieceMapPointDto> getAllMapPoints() {
        return artPieceRepository.findAllMapPoints();
    }
}
