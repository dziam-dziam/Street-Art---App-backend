package org.example.exceptions;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String cityName) {
        super("City: " + cityName + " was not found.");
    }
}
