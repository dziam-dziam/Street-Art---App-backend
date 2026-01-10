package org.example.services.geocoding_services;


import org.example.integrations.geocoding.GeocodeResult;

public interface GeocodingService {

    GeocodeResult geocode(String address);
}
