package org.example.services.get_services.get_all_services;

import org.example.dtos.artpiece.ArtPieceAdminDto;
import org.example.entities.ArtPiece;
import org.example.mappers.ArtPieceMapper;
import org.example.repositories.ArtPieceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllArtPiecesServiceTest {

    @Mock ArtPieceRepository artPieceRepository;
    @Mock ArtPieceMapper artPieceMapper;

    @InjectMocks GetAllArtPiecesService getAllArtPiecesService;

    @Test
    void should_return_list_of_responseDtos_when_artpieces_exist() {
        ArtPiece a1 = ArtPiece.builder().id(1L).build();
        ArtPiece a2 = ArtPiece.builder().id(2L).build();

        when(artPieceRepository.findAll()).thenReturn(List.of(a1, a2));

        when(artPieceMapper.mapArtPieceEntityToAdminDto(a1))
                .thenReturn(ArtPieceAdminDto.builder().artPieceName("A1").build());
        when(artPieceMapper.mapArtPieceEntityToAdminDto(a2))
                .thenReturn(ArtPieceAdminDto.builder().artPieceName("A2").build());

        List<ArtPieceAdminDto> result = getAllArtPiecesService.getAllArtPieces();

        assertEquals(2, result.size());
        assertEquals("A1", result.get(0).getArtPieceName());
        assertEquals("A2", result.get(1).getArtPieceName());

        verify(artPieceRepository).findAll();
        verify(artPieceMapper).mapArtPieceEntityToAdminDto(a1);
        verify(artPieceMapper).mapArtPieceEntityToAdminDto(a2);
    }

    @Test
    void should_return_empty_list_when_no_artpieces() {
        when(artPieceRepository.findAll()).thenReturn(List.of());

        List<ArtPieceAdminDto> result = getAllArtPiecesService.getAllArtPieces();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(artPieceRepository).findAll();
        verifyNoInteractions(artPieceMapper);
    }
}
