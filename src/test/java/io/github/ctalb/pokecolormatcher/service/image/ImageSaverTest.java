package io.github.ctalb.pokecolormatcher.service.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ImageSaverTest {

    @TempDir
    Path tempDir;

    private ImageSaver imageSaver;
    private String pokemonName;
    private byte[] expectedBytes;

    @BeforeEach
    void setUp() throws IOException {

        this.pokemonName = "test";
        this.expectedBytes = new byte[]{1, 2, 3, 4, 5};
        this.imageSaver = new ImageSaver(tempDir.toString());

    }

    @Test
    void givenFakeImage_whenSaveImage_thenReturnCorrectPath() {

        Path savedPath = imageSaver.saveImage(pokemonName, expectedBytes);
        Path expectedPath = tempDir.resolve(pokemonName + "_default.png");

        assertEquals(expectedPath.toString(), savedPath.toString(), "Returned path does not match expected path");

    }

    @Test
    void givenFakeImage_whenSaveImage_thenFileExists() {

        imageSaver.saveImage(pokemonName, expectedBytes);
        Path expectedPath = tempDir.resolve(pokemonName + "_default.png");

        assertTrue(Files.exists(expectedPath), "File does not exist");

    }

    @Test
    void givenFakeImage_whenSaveImage_thenReturnCorrectBytes() throws IOException {

        imageSaver.saveImage(pokemonName, expectedBytes);
        Path expectedPath = tempDir.resolve(pokemonName + "_default.png");
        byte[] actualBytes = Files.readAllBytes(expectedPath);

        assertArrayEquals(expectedBytes, actualBytes, "Expected bytes do not match actual bytes");
    }

}