package org.example.exceptions;

public class AppUserNotFoundException extends RuntimeException {
    public AppUserNotFoundException(String appUserEmail) {
        super("App user with email: " + appUserEmail + " was not found.");
    }
}
