package org.example.exceptions;

public class AppUserNotFoundByEmailException extends RuntimeException {
    public AppUserNotFoundByEmailException(String appUserEmail) {
        super("App user with email: " + appUserEmail + " was not found.");
    }
}
