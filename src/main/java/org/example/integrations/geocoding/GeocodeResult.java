package org.example.integrations.geocoding;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GeocodeResult {

    //TODO zmien moze na record zamiast class jak ci podpowiada

    double latitude;

    double longitude;

    String displayName;
}
