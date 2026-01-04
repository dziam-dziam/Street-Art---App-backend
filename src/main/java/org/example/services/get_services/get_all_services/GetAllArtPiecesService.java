package org.example.services.get_services.get_all_services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.artpiece.ResponseArtPieceDto;
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

    public List<ResponseArtPieceDto> getAllArtPieces() {
        List<ResponseArtPieceDto> artPieceDtos = new ArrayList<>();
        List<ArtPiece> artPieceEntities = artPieceRepository.findAll();
        for (ArtPiece artPieceEntity : artPieceEntities) {
            ResponseArtPieceDto artPieceResponseDto = artPieceMapper.mapArtPieceEntityToResponseDto(artPieceEntity);
            artPieceDtos.add(artPieceResponseDto);
        }
        return artPieceDtos;
    }
}
