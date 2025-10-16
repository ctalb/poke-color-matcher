package io.github.ctalb.pokecolormatcher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctalb.pokecolormatcher.model.PokemonMatchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ResultStorageService {

    private final ObjectMapper objectMapper;

    public ResultStorageService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Value("${results.folder}")
    private String destinationFolder;

    public void saveResult(PokemonMatchResult result, String pokemonName) throws IOException {

        String fileName =  pokemonName + "_default_result.json";
        Path directory = Paths.get(destinationFolder);
        Path filePath = directory.resolve(fileName);
        objectMapper.writeValue(filePath.toFile(), result);

    }
}
