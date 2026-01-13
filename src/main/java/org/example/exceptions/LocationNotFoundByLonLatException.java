package org.example.exceptions;

public class LocationNotFoundByLonLatException extends RuntimeException {
    public LocationNotFoundByLonLatException(double latitude,double longitude) {
        super("Location with latitude: " + latitude + " and longitude: " + longitude + " was not found.");
    }
}
