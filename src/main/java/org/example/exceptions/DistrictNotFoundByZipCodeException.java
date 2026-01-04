package org.example.exceptions;

public class DistrictNotFoundByZipCodeException extends RuntimeException {
    public DistrictNotFoundByZipCodeException(String districtZipCode) {
        super("District with zip code: " + districtZipCode + "was not found");
    }
}
