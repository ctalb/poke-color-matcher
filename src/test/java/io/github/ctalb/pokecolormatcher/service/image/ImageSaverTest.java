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
    private String fileName;
    private byte[] expectedBytes;

    @BeforeEach
    void setUp() throws IOException {

        this.fileName = "test.png";
        this.expectedBytes = new byte[]{1, 2, 3, 4, 5};
        this.imageSaver = new ImageSaver();

    }

    @Test
    void givenFakeImage_whenSaveImage_thenReturnCorrectPath() {

        String savedPath = imageSaver.saveImage(fileName, tempDir.toString(), expectedBytes);
        Path expectedPath = tempDir.resolve(fileName);

        assertEquals(expectedPath.toString(), savedPath, "Returned path does not match expected path");

    }

    @Test
    void givenFakeImage_whenSaveImage_thenFileExists() {

        imageSaver.saveImage(fileName, tempDir.toString(), expectedBytes);
        Path expectedPath = tempDir.resolve(fileName);

        assertTrue(Files.exists(expectedPath), "File does not exist");

    }

    @Test
    void givenFakeImage_whenSaveImage_thenReturnCorrectBytes() throws IOException {

        imageSaver.saveImage(fileName, tempDir.toString(), expectedBytes);
        Path expectedPath = tempDir.resolve(fileName);
        byte[] actualBytes = Files.readAllBytes(expectedPath);

        assertArrayEquals(expectedBytes, actualBytes, "Expected bytes do not match actual bytes");
    }

}