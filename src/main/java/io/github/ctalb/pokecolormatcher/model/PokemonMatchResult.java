package io.github.ctalb.pokecolormatcher.model;

import java.util.List;

public record PokemonMatchResult(String name, String image, List<FlossColorMatch> matches) {
}
