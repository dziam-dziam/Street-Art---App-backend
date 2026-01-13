package org.example.exceptions;

public class PhotoNotFoundByUrlException extends RuntimeException {
    public PhotoNotFoundByUrlException(String photoUrl) {
        super("Photo with url: " + photoUrl + " was not found");
    }
}
