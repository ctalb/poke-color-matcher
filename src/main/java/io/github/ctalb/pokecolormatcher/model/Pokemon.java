package io.github.ctalb.pokecolormatcher.model;

/**
 * Represents the relevant fields of a Pokémon response from PokéAPI.
 * Only the sprites field is captured as it is the only field used by this application.
 */
public record Pokemon(Sprites sprites) {
}
