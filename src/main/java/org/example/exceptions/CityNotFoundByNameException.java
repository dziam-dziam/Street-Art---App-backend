package org.example.exceptions;

public class CityNotFoundByNameException extends RuntimeException {
    public CityNotFoundByNameException(String cityName) {
        super("City: " + cityName + " was not found.");
    }
}
