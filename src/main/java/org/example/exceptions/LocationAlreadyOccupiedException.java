package org.example.exceptions;

import org.example.entities.ArtPiece;
import org.example.entities.Location;

import java.util.List;

public class LocationAlreadyOccupiedException extends RuntimeException {
    public LocationAlreadyOccupiedException(List<ArtPiece> artPiecesPresent, Location locationBeingChecked) {
        super("Location with latitude: " + locationBeingChecked.getLocationLatitude()
                + " and longitude: " + locationBeingChecked.getLocationLongitude() +
                " has already artpieces assigned to it");
        for (ArtPiece artPiece : artPiecesPresent){
            System.out.println(artPiece);
        }
    }
}
