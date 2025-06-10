package io.github.ctalb.pokecolormatcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Other(@JsonProperty("official-artwork") OfficialArtwork officialArtwork) {
}
