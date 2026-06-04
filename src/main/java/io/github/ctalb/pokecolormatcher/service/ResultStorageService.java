package io.github.ctalb.pokecolormatcher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctalb.pokecolormatcher.model.PokemonMatchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service responsible for managing PokemonMatchResults:
 * records containing a Pokémon's name, image path, and list of extracted colors with their matched DMC flosses.
 */
@Service
public class ResultStorageService {

    private final String folder;
    private final ObjectMapper objectMapper;

    /**
     * @param folder the directory path where Pokémon results are saved, injected from application properties.
     * @param objectMapper the Jackson ObjectMapper used for JSON serialization and deserialization.
     */
    public ResultStorageService(@Value("${results.folder}") String folder,ObjectMapper objectMapper) {
        this.folder = folder;
        this.objectMapper = objectMapper;
    }

    /**
     * Saves the PokemonMatchResult.
     * @param result the PokemonMatchResult to be saved.
     * @param pokemonName the Pokémon name, used to generate the result's file name.
     * @throws IOException if the file cannot be written.
     */
    public void saveResult(PokemonMatchResult result, String pokemonName) throws IOException {

        String fileName =  pokemonName + "_default_result.json";
        Path directory = Paths.get(folder);
        Files.createDirectories(directory);
        Path filePath = directory.resolve(fileName);
        objectMapper.writeValue(filePath.toFile(), result);

    }

    /**
     * Attempts to retrieve the given Pokémon's PokemonMatchResult.
     * @param pokemonName the Pokémon name, used to generate the result's file name.
     * @return the PokemonMatchResult for the given Pokémon, or null if no result has been saved yet.
     * @throws IOException if the file cannot be read.
     */
    public PokemonMatchResult readResult(String pokemonName) throws IOException {

        String fileName =  pokemonName + "_default_result.json";
        Path directory = Paths.get(folder);
        Files.createDirectories(directory);
        Path filePath = directory.resolve(fileName);

        if (Files.exists(filePath)) {

            return objectMapper.readValue(filePath.toFile(), PokemonMatchResult.class);
        }

        return null;
    }
}
