package io.github.ctalb.pokecolormatcher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctalb.pokecolormatcher.model.PokemonMatchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ResultStorageService {

    private final String folder;
    private final ObjectMapper objectMapper;

    public ResultStorageService(@Value("${results.folder}") String folder,ObjectMapper objectMapper) {
        this.folder = folder;
        this.objectMapper = objectMapper;
    }

    public void saveResult(PokemonMatchResult result, String pokemonName) throws IOException {

        String fileName =  pokemonName + "_default_result.json";
        Path directory = Paths.get(folder);
        Path filePath = directory.resolve(fileName);
        objectMapper.writeValue(filePath.toFile(), result);

    }

    public PokemonMatchResult readResult(String pokemonName) throws IOException {

        String fileName =  pokemonName + "_default_result.json";
        Path directory = Paths.get(folder);
        Path filePath = directory.resolve(fileName);

        if (Files.exists(filePath)) {

            return objectMapper.readValue(filePath.toFile(), PokemonMatchResult.class);
        }

        return null;
    }
}
