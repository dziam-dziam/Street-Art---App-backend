package org.example.integrations.geocoding;

import lombok.Builder;

@Builder
public record GeocodeResult(double latitude, double longitude, String displayName) {

}
