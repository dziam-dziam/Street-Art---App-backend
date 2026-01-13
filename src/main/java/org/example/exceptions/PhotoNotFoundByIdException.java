package org.example.exceptions;

public class PhotoNotFoundByIdException extends RuntimeException {
    public PhotoNotFoundByIdException(Long photoId) {
        super("Photo with id: " + photoId + " was not found");
    }
}
