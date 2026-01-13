package org.example.services.fix_admin_services;

import lombok.RequiredArgsConstructor;
import org.example.entities.Photo;
import org.example.exceptions.PhotoNotFoundByIdException;
import org.example.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemovePhotoService {

    private final PhotoRepository photoRepository;

    public void removeInvalidPhotoById(Long photoId){
        Photo photoToBeRemoved = photoRepository.findById(photoId)
                .orElseThrow(() -> new PhotoNotFoundByIdException(photoId));

        photoRepository.delete(photoToBeRemoved);
    }
}
