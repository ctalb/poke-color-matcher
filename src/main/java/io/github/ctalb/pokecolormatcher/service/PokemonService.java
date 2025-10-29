package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.Pokemon;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.buffer.DataBufferLimitException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class PokemonService {

    @Qualifier("pokeApiClient")
    private final WebClient webClient;

    public PokemonService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Cacheable("pokemonSpriteDefault")
    public String getSpriteDefaultUrl(String name) {
        try {
            Pokemon pokemon = webClient.get().uri("/pokemon/{name}", name)
                    .retrieve().bodyToMono(Pokemon.class).block();
            if (pokemon == null || pokemon.sprites() == null || pokemon.sprites().frontDefault() == null) {
                return null;
            }

            return pokemon.sprites().frontDefault();
        } catch (WebClientResponseException e) {
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
