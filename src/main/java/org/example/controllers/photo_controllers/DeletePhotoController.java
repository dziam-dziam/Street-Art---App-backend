package org.example.controllers.photo_controllers;

import lombok.RequiredArgsConstructor;
import org.example.services.PhotoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos/delete")
public class DeletePhotoController {

    private final PhotoService photoService;

    //TODO dobrze by bylo dla tej voidówki dac info ze sie udalo lub nie usuwanie dla uzytkownika jezeli on tym
    // manewruje
    @DeleteMapping("/{photoId}")
    public void delete(@PathVariable Long photoId) {
        photoService.deletePhoto(photoId);
    }
}
