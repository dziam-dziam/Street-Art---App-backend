package org.example.exceptions;

public class DistrictNotFoundByIdException extends RuntimeException {
    public DistrictNotFoundByIdException(Long id) {
        super("District with id: " + id + " was not found");
    }
}
