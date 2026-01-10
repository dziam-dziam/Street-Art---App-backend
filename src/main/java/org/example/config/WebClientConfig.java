package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient nominatimWebClient() {
        return WebClient.builder()
                .baseUrl("https://nominatim.openstreetmap.org")
                .defaultHeader(HttpHeaders.USER_AGENT, "StreetApp/1.0 (contact: damianzmudzinski3@gmail.com)")
                .build();
    }
}
