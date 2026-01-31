package org.example.controllers.photo_controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.photo.PhotoResponseDto;
import org.example.entities.Photo;
import org.example.services.PhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos/upload")
public class UploadPhotoController {

    private final PhotoService photoService;

    @PostMapping(value = "/{artPieceId}/photos", consumes = "multipart/form-data")
    public PhotoResponseDto uploadPhoto(@PathVariable Long artPieceId,
                                        @RequestParam("image") MultipartFile file) throws Exception {
        try {
            Photo saved = photoService.uploadPhotoToArtPiece(artPieceId, file);
            return PhotoResponseDto.builder()
                    .id(saved.getId())
                    .fileName(saved.getFileName())
                    .contentType(saved.getContentType())
                    .sizeBytes(saved.getSizeBytes())
                    .downloadUrl("/api/photos/download/" + saved.getId())
                    .build();
        } catch (Exception exception) {
            throw new Exception("There was an issue at uploading photo");
        }
    }
}
