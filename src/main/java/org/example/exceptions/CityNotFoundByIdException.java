package org.example.exceptions;

public class CityNotFoundByIdException extends RuntimeException {
    public CityNotFoundByIdException(Long id) {
        super("City with id: " + id + " was not found");
    }
}
