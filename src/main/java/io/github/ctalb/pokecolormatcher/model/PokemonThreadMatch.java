package io.github.ctalb.pokecolormatcher.model;

import java.util.List;

public record PokemonThreadMatch(String name, String image, List<PaletteMatch> matches) {
}
