package io.github.ctalb.pokecolormatcher.model;

import java.util.List;

/**
 * Represents a Pokémon's match result with the Pokémon's name, its image path, and the list of colors
 * with their associated DMC floss matches.
 */
public record PokemonMatchResult(String name, String imagePath, List<FlossColorMatch> matches) {
}
