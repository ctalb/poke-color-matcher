package io.github.ctalb.pokecolormatcher.service.image;

import io.github.ctalb.pokecolormatcher.service.PokemonService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ImageDownloader {

    private WebClient webClient;
    private PokemonService pokemonService;

    public ImageDownloader(WebClient.Builder webClientBuilder, PokemonService pokemonService) {
        this.webClient = webClientBuilder.build();
        this.pokemonService = pokemonService;
    }


}
