package org.example.integrations.geocoding;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GeocodeResult {
    double latitude;

    double longitude;

    String displayName;
}
