package org.example.services.get_services.get_districts_artpieces_services;

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
public class GetJezyceArtPiecesService {

    private final ArtPieceRepository artPieceRepository;
    private final ArtPieceMapper artPieceMapper;

    public List<ResponseArtPieceDto> getJezyceArtpieces(){
        List<ArtPiece> jezyceArtPieces = artPieceRepository.getArtPiecesFromDistrict("Jeżyce");
        List<ResponseArtPieceDto> jezyceArtPiecesResponseDtos = new ArrayList<>();
        for (ArtPiece artPieceEntity : jezyceArtPieces){
            ResponseArtPieceDto responseArtPieceDto = artPieceMapper.mapArtPieceEntityToResponseDto(artPieceEntity);
            jezyceArtPiecesResponseDtos.add(responseArtPieceDto);
        }
        return jezyceArtPiecesResponseDtos;
    }
}
