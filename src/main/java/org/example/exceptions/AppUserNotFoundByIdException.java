package org.example.exceptions;

public class AppUserNotFoundByIdException extends RuntimeException {
    public AppUserNotFoundByIdException(Long appUserId) {
        super("App user with id: " + appUserId + " was not found");
    }
}
