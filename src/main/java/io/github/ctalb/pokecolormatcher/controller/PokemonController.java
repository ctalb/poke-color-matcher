package io.github.ctalb.pokecolormatcher.controller;

import io.github.ctalb.pokecolormatcher.model.PokemonMatchResult;
import io.github.ctalb.pokecolormatcher.service.PokemonMatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokemonMatchService pokemonMatchService;

    public PokemonController(PokemonMatchService pokemonMatchService) {
        this.pokemonMatchService = pokemonMatchService;
    }

    @GetMapping("/{name}/Match")
    public ResponseEntity<PokemonMatchResult> getMatch(@PathVariable String name) {

        try {

            PokemonMatchResult result = pokemonMatchService.getMatchResult(name);
            return ResponseEntity.ok(result);

        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check")
    public String healthCheck() {
        return "OK";
    }


}
