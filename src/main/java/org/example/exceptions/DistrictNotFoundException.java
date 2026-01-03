package org.example.exceptions;

public class DistrictNotFoundException extends RuntimeException {
    public DistrictNotFoundException(String districtName) {
        super("District: " + districtName + " was not found.");
    }
}
