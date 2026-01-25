package org.example.services.get_services.get_districts_artpieces_services;

import org.example.dtos.artpiece.ResponseArtPieceDto;
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
class GetJezyceArtPiecesServiceTest {

    @Mock ArtPieceRepository artPieceRepository;
    @Mock ArtPieceMapper artPieceMapper;

    @InjectMocks GetJezyceArtPiecesService getJezyceArtPiecesService;

    @Test
    void should_return_jezyce_artpieces_as_responseDtos() {
        ArtPiece a1 = ArtPiece.builder().id(1L).build();
        when(artPieceRepository.getArtPiecesFromDistrict("Jeżyce"))
                .thenReturn(List.of(a1));

        when(artPieceMapper.mapArtPieceEntityToResponseDto(a1))
                .thenReturn(ResponseArtPieceDto.builder().artPieceDistrict("Jeżyce").build());

        List<ResponseArtPieceDto> result = getJezyceArtPiecesService.getJezyceArtpieces();

        assertEquals(1, result.size());
        assertEquals("Jeżyce", result.get(0).getArtPieceDistrict());

        verify(artPieceRepository).getArtPiecesFromDistrict("Jeżyce");
        verify(artPieceMapper).mapArtPieceEntityToResponseDto(a1);
    }

    @Test
    void should_return_empty_list_when_no_jezyce_artpieces() {
        when(artPieceRepository.getArtPiecesFromDistrict("Jeżyce"))
                .thenReturn(List.of());

        List<ResponseArtPieceDto> result = getJezyceArtPiecesService.getJezyceArtpieces();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(artPieceRepository).getArtPiecesFromDistrict("Jeżyce");
        verifyNoInteractions(artPieceMapper);
    }
}
