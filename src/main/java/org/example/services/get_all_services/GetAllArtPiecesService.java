package org.example.services.get_all_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ArtPieceDto;
import org.example.entities.ArtPiece;
import org.example.mappers.ArtPieceMapper;
import org.example.repositories.ArtPieceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllArtPiecesService {

    private final ArtPieceRepository artPieceRepository;
    private final ArtPieceMapper artPieceMapper;

    public List<ArtPieceDto> getAllArtPieces() {
        List<ArtPieceDto> artPieceDtos = new ArrayList<>();
        List<ArtPiece> artPieceEntities = artPieceRepository.findAll();
        for (ArtPiece artPieceEntity : artPieceEntities) {
            ArtPieceDto artPieceDto = artPieceMapper.mapArtPieceEntityToArtPieceDto(artPieceEntity);
            artPieceDtos.add(artPieceDto);
        }
        return artPieceDtos;
    }
}
