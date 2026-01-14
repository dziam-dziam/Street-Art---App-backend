package org.example.controllers.photo_controllers;

import lombok.RequiredArgsConstructor;
import org.example.services.PhotoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos/download")
public class DownloadPhotoController {

    private final PhotoService photoService;

    @GetMapping("/{photoId}")
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable Long photoId) {
        var photo = photoService.getPhoto(photoId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(photo.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + photo.getFileName().replace("\"", "") + "\"")
                .body(photo.getImageData());
    }
}

