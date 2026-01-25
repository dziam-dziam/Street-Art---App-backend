package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entities.ArtPiece;
import org.example.entities.Photo;
import org.example.exceptions.ArtPieceNotFoundByIdException;
import org.example.exceptions.PhotoNotFoundByIdException;
import org.example.repositories.ArtPieceRepository;
import org.example.repositories.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final ArtPieceRepository artPieceRepository;

    //TODO finals

    @Transactional
    public Photo uploadPhotoToArtPiece(Long artPieceId, MultipartFile file) throws IOException {
        ArtPiece artPiece = artPieceRepository.findById(artPieceId)
                .orElseThrow(() -> new ArtPieceNotFoundByIdException(artPieceId));

        Photo photo = Photo.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType() == null ? "application/octet-stream" : file.getContentType())
                .sizeBytes(file.getSize())
                .imageData(file.getBytes())
                .artPieceOnPhoto(artPiece)
                .build();

        artPiece.addPhoto(photo);

        return photoRepository.save(photo);
    }

    @Transactional(readOnly = true)
    public Photo getPhoto(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new PhotoNotFoundByIdException(photoId));
    }

    @Transactional(readOnly = true)
    public Set<Photo> getArtPiecePhotos(Long artPieceId) {
        return photoRepository.findAllByArtPieceOnPhoto_Id(artPieceId);
    }

    @Transactional
    public void deletePhoto(Long photoId) {
        Photo photo = getPhoto(photoId);
        ArtPiece artPiece = photo.getArtPieceOnPhoto();

        if (artPiece != null) {
            artPiece.removePhoto(photo);
            artPieceRepository.save(artPiece);
        } else {
            photoRepository.delete(photo);
        }

    }
}

