package org.example.services.geocoding_services;

import org.example.integrations.geocoding.GeocodeResult;
import org.example.integrations.geocoding.NominatimGeocodeItem;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class NominatimGeocodingService implements GeocodingService {

    private final WebClient webClient;

    public NominatimGeocodingService(WebClient nominatimWebClient) {
        this.webClient = nominatimWebClient;
    }

    @Override
    public GeocodeResult geocode(String address) {
        if (address == null || address.isBlank())
            throw new IllegalArgumentException("Address is null/blank");


        List<NominatimGeocodeItem> items = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", address)      // <- surowy string, bez encode
                        .queryParam("format", "json")
                        .queryParam("limit", 1)
                        .build())
                .retrieve()
                .bodyToFlux(NominatimGeocodeItem.class)
                .collectList()
                .block();

        if (items == null || items.isEmpty()){
            throw new IllegalArgumentException("Geocoding failed, no results for address : " + address);
        }

        NominatimGeocodeItem firstResponseItem = items.get(0);

        double responseLatitude = Double.parseDouble(firstResponseItem.getLatitude());
        double responseLongitude = Double.parseDouble(firstResponseItem.getLongitude());
        String responseItemDisplayName = firstResponseItem.getDisplayName();

        return GeocodeResult.builder()
                .latitude(responseLatitude)
                .longitude(responseLongitude)
                .displayName(responseItemDisplayName)
                .build();
    }
}
