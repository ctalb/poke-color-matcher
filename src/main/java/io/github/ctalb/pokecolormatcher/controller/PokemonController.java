package io.github.ctalb.pokecolormatcher.controller;

import io.github.ctalb.pokecolormatcher.model.PokemonMatchResult;
import io.github.ctalb.pokecolormatcher.service.PokemonMatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * REST controller for handling Pokémon floss-matching requests.
 * All endpoints are mapped under /api/pokemon.
 */
@CrossOrigin (origins="${allowed.origins}")
@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokemonMatchService pokemonMatchService;

    public PokemonController(PokemonMatchService pokemonMatchService) {
        this.pokemonMatchService = pokemonMatchService;
    }

    /**
     * Retrieves the floss color match result for the given Pokémon.
     * @param name the Pokémon name.
     * @return a 200 response with the PokemonMatchResult, or a 404 if the Pokémon is not found.
     */
    @GetMapping("/{name}/match")
    public ResponseEntity<PokemonMatchResult> getMatch(@PathVariable String name) {

        try {

            PokemonMatchResult result = pokemonMatchService.getMatchResult(name);
            return ResponseEntity.ok(result);

        } catch (IOException e) {
            return ResponseEntity.notFound().build();

        } catch (RuntimeException e) {
            return ResponseEntity.status(503).build();
        }
    }

    /**
     * Simple check to confirm that the backend is running.
     * @return the string "OK" if the backend is running.
     */
    @GetMapping("/check")
    public String healthCheck() {
        return "OK";
    }

}
