package org.example.exceptions;

public class PhotoNotFoundException extends RuntimeException {
    public PhotoNotFoundException(String photoUrl) {
        super("Photo with url: " + photoUrl + " was not found");
    }
}
