package io.github.ctalb.pokecolormatcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the sprites object from a PokéAPI Pokémon response.
 */
public record Sprites(@JsonProperty("front_default") String frontDefault) {
}
