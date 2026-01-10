package org.example.integrations.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NominatimGeocodeItem {

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lon")
    private String longitude;

    @JsonProperty("display_name")
    private String displayName;
}
