package io.github.ctalb.pokecolormatcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OfficialArtwork(@JsonProperty("front_default") String frontDefault) {
}
