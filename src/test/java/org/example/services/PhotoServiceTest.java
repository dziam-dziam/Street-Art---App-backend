package org.example.services;

import org.example.entities.ArtPiece;
import org.example.entities.Photo;
import org.example.exceptions.ArtPieceNotFoundByIdException;
import org.example.exceptions.PhotoNotFoundByIdException;
import org.example.repositories.ArtPieceRepository;
import org.example.repositories.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhotoServiceTest {

    @Mock
    PhotoRepository photoRepository;

    @Mock
    ArtPieceRepository artPieceRepository;

    @InjectMocks
    PhotoService photoService;

    @Test
    void should_upload_photo_and_attach_to_art_piece() throws IOException {
        // given
        Long artPieceId = 1L;

        ArtPiece artPiece = ArtPiece.builder()
                .id(artPieceId)
                .build();

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "pic.png",
                null, // <- wymuszamy fallback
                "hello".getBytes()
        );

        when(artPieceRepository.findById(artPieceId))
                .thenReturn(Optional.of(artPiece));

        when(photoRepository.save(any(Photo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Photo result = photoService.uploadPhotoToArtPiece(artPieceId, file);

        // then
        assertNotNull(result);
        assertEquals("pic.png", result.getFileName());
        assertEquals("application/octet-stream", result.getContentType()); // fallback
        assertEquals(file.getSize(), result.getSizeBytes());
        assertArrayEquals(file.getBytes(), result.getImageData());
        assertSame(artPiece, result.getArtPieceOnPhoto());

        verify(artPieceRepository).findById(artPieceId);
        verify(photoRepository).save(any(Photo.class));
    }

    @Test
    void should_upload_photo_with_default_content_type_when_null() throws IOException {
        Long artPieceId = 2L;
        ArtPiece artPiece = ArtPiece.builder().id(artPieceId).build();

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "no-type.bin",
                null,
                "xyz".getBytes(StandardCharsets.UTF_8)
        );

        when(artPieceRepository.findById(artPieceId)).thenReturn(Optional.of(artPiece));
        when(photoRepository.save(any(Photo.class))).thenAnswer(inv -> inv.getArgument(0));

        Photo saved = photoService.uploadPhotoToArtPiece(artPieceId, file);

        assertEquals("application/octet-stream", saved.getContentType());
    }


    @Test
    void should_throw_when_upload_photo_art_piece_not_found() {
        // given
        Long artPieceId = 999L;
        MockMultipartFile file = new MockMultipartFile(
                "file", "x.jpg", "image/jpeg", "data".getBytes()
        );

        when(artPieceRepository.findById(artPieceId)).thenReturn(Optional.empty());

        // when + then
        ArtPieceNotFoundByIdException exception = assertThrows(ArtPieceNotFoundByIdException.class,
                () -> photoService.uploadPhotoToArtPiece(artPieceId, file));

        assertTrue(exception.getMessage().contains("Art piece with id: " + artPieceId + " was not found"));
        verify(photoRepository, never()).save(any());
    }

    @Test
    void should_throw_when_get_photo_not_found() {
        // given
        Long photoId = 123L;
        when(photoRepository.findById(photoId)).thenReturn(Optional.empty());

        // when + then
        PhotoNotFoundByIdException ex = assertThrows(PhotoNotFoundByIdException.class,
                () -> photoService.getPhoto(photoId));

        assertTrue(ex.getMessage().contains("Photo with id: " + photoId + " was not found"));
        verify(photoRepository).findById(photoId);
    }

    @Test
    void should_delete_photo_and_save_art_piece_when_photo_has_art_piece_assigned() {
        // given
        Long photoId = 10L;

        ArtPiece artPiece = ArtPiece.builder()
                .id(1L)
                .build();

        Photo photo = Photo.builder()
                .id(photoId)
                .artPieceOnPhoto(artPiece)
                .build();

        when(photoRepository.findById(photoId)).thenReturn(Optional.of(photo));
        when(artPieceRepository.save(artPiece)).thenReturn(artPiece);

        // when
        photoService.deletePhoto(photoId);

        // then
        verify(photoRepository).findById(photoId);
        verify(artPieceRepository).save(artPiece);
        verify(photoRepository, never()).delete(any());
    }

    @Test
    void should_delete_photo_when_photo_has_no_art_piece_assigned() {
        Long photoId = 10L;

        Photo photo = Photo.builder().id(photoId).artPieceOnPhoto(null).build();
        when(photoRepository.findById(photoId)).thenReturn(Optional.of(photo));

        photoService.deletePhoto(photoId);

        verify(photoRepository).delete(photo);
        verify(artPieceRepository, never()).save(any());
    }
}