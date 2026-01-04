package org.example.exceptions;

public class DistrictNotFoundByNameException extends RuntimeException {
    public DistrictNotFoundByNameException(String districtName) {
        super("District: " + districtName + " was not found.");
    }
}
