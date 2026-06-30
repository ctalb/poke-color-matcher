package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.exception.PokemonNotFoundException;
import io.github.ctalb.pokecolormatcher.model.Pokemon;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.buffer.DataBufferLimitException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * Service responsible for retrieving Pokémon data from PokéAPI.
 */
@Service
public class PokemonService {

    @Qualifier("pokeApiClient")
    private final WebClient webClient;

    /**
     * @param webClient a WebClient preconfigured with the PokéAPI base URL.
     */
    public PokemonService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Retrieves the URL of the default front sprite for a given Pokémon from PokéAPI.
     * @param name the Pokémon name.
     * @return the URL of the default front sprite, or null if the Pokémon is not found or the sprite is unavailable.
     */
    public String getSpriteDefaultUrl(String name) {
        try {
            Pokemon pokemon = webClient.get().uri("/pokemon/{name}", name)
                    .retrieve().bodyToMono(Pokemon.class).block();
            if (pokemon == null || pokemon.sprites() == null || pokemon.sprites().frontDefault() == null) {
                return null;
            }

            return pokemon.sprites().frontDefault();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().value() == 404) {
                throw new PokemonNotFoundException(name);
            }

            System.err.println("WebClient error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        } catch (DataBufferLimitException e) {
            System.err.println("Response too large: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Exception caught: " + e.getMessage());
            return null;
        }
    }
}
