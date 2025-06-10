package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PokemonService {

    @Autowired
    @Qualifier("pokeApiClient")
    private WebClient webClient;

    @Cacheable("pokemonArtwork")
    public String getOfficialArtworkUrl(String name) {
        Pokemon pokemon = webClient.get().uri("/pokemon/{name}", name)
                .retrieve().bodyToMono(Pokemon.class).block();
        if (pokemon == null || pokemon.sprites() == null ||
                pokemon.sprites().other() == null ||
                pokemon.sprites().other().officialArtwork() == null ||
                pokemon.sprites().other().officialArtwork().frontDefault() == null) {
            return null;
        }

        return pokemon.sprites().other().officialArtwork().frontDefault();
    }
}
