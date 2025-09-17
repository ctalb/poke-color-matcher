package io.github.ctalb.pokecolormatcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Sprites(@JsonProperty("front_default") String frontDefault) {
}
