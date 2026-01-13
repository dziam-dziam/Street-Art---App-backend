package org.example.exceptions;

public class ArtPieceNotFoundByIdException extends RuntimeException {
    public ArtPieceNotFoundByIdException(Long artPieceId) {
        super("Art piece with id: " + artPieceId + " was not found");
    }
}
