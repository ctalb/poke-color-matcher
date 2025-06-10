package io.github.ctalb.pokecolormatcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OfficialArtwork(@JsonProperty("front_default") String frontDefault) {
}
