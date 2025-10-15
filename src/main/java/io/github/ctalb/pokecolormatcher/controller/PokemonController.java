package io.github.ctalb.pokecolormatcher.controller;

import io.github.ctalb.pokecolormatcher.model.PokemonMatchResult;
import io.github.ctalb.pokecolormatcher.service.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/{name}/sprite")
    public ResponseEntity<String> getArtwork(@PathVariable String name) {
        String url = pokemonService.getSpriteDefaultUrl(name);

        if (url == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(url);
    }

    @GetMapping("/{name}/Match")
    public ResponseEntity<PokemonMatchResult> getMatch(@PathVariable String name) {
        // Call PokemonMatchService.getMatch()
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/check")
    public String healthCheck() {
        return "OK";
    }


}
