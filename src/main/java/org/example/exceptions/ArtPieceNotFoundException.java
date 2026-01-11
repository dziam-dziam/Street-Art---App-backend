package org.example.exceptions;

public class ArtPieceNotFoundException extends RuntimeException {
    public ArtPieceNotFoundException(Long id) {
        super("ArtPieceEntity with id: " + id + " was not found.");
    }
}
