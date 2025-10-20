package io.github.ctalb.pokecolormatcher.model;

import java.util.List;

public record PokemonMatchResult(String name, String imagePath, List<FlossColorMatch> matches) {
}
