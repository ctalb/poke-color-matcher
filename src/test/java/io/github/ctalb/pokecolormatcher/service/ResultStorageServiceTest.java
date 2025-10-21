package io.github.ctalb.pokecolormatcher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctalb.pokecolormatcher.model.DmcFloss;
import io.github.ctalb.pokecolormatcher.model.FlossColorMatch;
import io.github.ctalb.pokecolormatcher.model.PokemonMatchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultStorageServiceTest {

    @TempDir
    Path tempDir;

    private ResultStorageService resultStorageService;
    ObjectMapper objectMapper;
    private PokemonMatchResult expectedResult;
    private String pokemonName;


    @BeforeEach
    void setUp() {

        this.pokemonName = "test";
        String imagePath = "images/test.png";
        List<FlossColorMatch> matches = List.of(
                new FlossColorMatch(new int[]{255, 0, 0}, new DmcFloss("666", "Bright Red", "#e02d27")),
                new FlossColorMatch(new int[]{0, 255, 0},new DmcFloss("703", "Chartreuse", "#83b247")),
                new FlossColorMatch(new int[]{0, 0, 255}, new DmcFloss("798", "Dark Delft Blue", "#2763b0"))
        );

        this.expectedResult = new PokemonMatchResult(pokemonName, imagePath, matches);

        this.objectMapper = new ObjectMapper();

        this.resultStorageService = new ResultStorageService(tempDir.toString(), objectMapper);

    }

    @Test
    void givenResult_whenSaveResult_thenFileExists() throws IOException {

        resultStorageService.saveResult(expectedResult, pokemonName);
        Path expectedPath = tempDir.resolve(pokemonName + "_default_result.json");
        assertTrue(Files.exists(expectedPath), "File does not exist");
    }

    @Test
    void givenResult_whenSaveResult_thenFileContainsCorrectJson() throws IOException {

        resultStorageService.saveResult(expectedResult, pokemonName);

        Path path = tempDir.resolve(pokemonName + "_default_result.json");
        PokemonMatchResult deserializedResult = objectMapper.readValue(path.toFile(), PokemonMatchResult.class);

        assertEquals(expectedResult, deserializedResult);
    }

    @Test
    void givenName_whenReadResult_thenReturnNull() throws IOException {

        assertNull(resultStorageService.readResult(pokemonName));
    }

    @Test
    void givenName_whenReadResult_thenFileContainsCorrectJson() throws IOException {

        String fileName =  pokemonName + "_default_result.json";
        Path filePath = tempDir.resolve(fileName);
        objectMapper.writeValue(filePath.toFile(), expectedResult);

        PokemonMatchResult deserializedResult = resultStorageService.readResult(pokemonName);
        assertEquals(expectedResult, deserializedResult);
    }

    @Test
    void givenResult_whenSaveResult_givenName_whenReadResult_thenFileContainsCorrectJson() throws IOException {

        resultStorageService.saveResult(expectedResult, pokemonName);
        PokemonMatchResult deserializedResult = resultStorageService.readResult(pokemonName);
        assertEquals(expectedResult, deserializedResult);
    }

}