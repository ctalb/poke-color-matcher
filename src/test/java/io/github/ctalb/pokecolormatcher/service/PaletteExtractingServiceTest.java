package io.github.ctalb.pokecolormatcher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PaletteExtractingServiceTest {

    @TempDir
    Path tempDir;

    private PaletteExtractingService paletteExtractingService;
    private File testImageFile;

    @BeforeEach
    void setUp() throws IOException {

        paletteExtractingService = new PaletteExtractingService();

        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        Graphics  g = testImage.createGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, 100, 50);
        g.setColor(Color.GREEN);
        g.fillRect(0, 50, 100, 50);

        Path path = tempDir.resolve("test.png");
        ImageIO.write(testImage, "png", path.toFile());
        testImageFile = path.toFile();

    }

    @Test
    void givenTestImage_whenExtractPalette_thenReturnArrayNotEmpty() {
        try {
            int [][] testPalette = paletteExtractingService.extractPalette(testImageFile, 2);

            assertNotNull(testPalette);
            assertNotEquals(0, testPalette.length);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}