package io.github.ctalb.pokecolormatcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for building the PokéAPI WebClient.
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates a WebClient preconfigured with the PokéAPI base URL.
     * The memory limit is increased from the default to accommodate large API responses.
     * @return a WebClient configured for use with PokéAPI.
     */
    @Bean
    public WebClient pokeApiClient() {
        return  WebClient.builder()
                .baseUrl("https://pokeapi.co/api/v2")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();
    }
}
