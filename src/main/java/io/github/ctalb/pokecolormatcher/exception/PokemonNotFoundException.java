package io.github.ctalb.pokecolormatcher.exception;

public class PokemonNotFoundException extends RuntimeException{
    public PokemonNotFoundException(String pokemonName) {
        super("Pokémon not found: " + pokemonName);
    }
}
