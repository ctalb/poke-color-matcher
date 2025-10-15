package io.github.ctalb.pokecolormatcher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

            assertNotNull(testPalette, "Palette is null");
            assertNotEquals(0, testPalette.length, "Palette length is 0");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void givenTestImage_whenExtractPalette_thenReturnExpectedRed() throws IOException {

        int [][] testPalette = paletteExtractingService.extractPalette(testImageFile, 2);

        int [] expectedRed = {255, 0, 0};
        int [] actualRed = testPalette[0].clone();

        assertEquals(expectedRed[0], actualRed[0], 10);
        assertEquals(expectedRed[1], actualRed[1], 10);
        assertEquals(expectedRed[2], actualRed[2], 10);

    }

    @Test
    void givenTestImage_whenExtractPalette_thenReturnExpectedGreen() throws IOException {

        int [][] testPalette = paletteExtractingService.extractPalette(testImageFile, 2);

        int [] expectedGreen = {0, 255, 0};
        int [] actualGreen = testPalette[1].clone();

        assertEquals(expectedGreen[0], actualGreen[0], 10);
        assertEquals(expectedGreen[1], actualGreen[1], 10);
        assertEquals(expectedGreen[2], actualGreen[2], 10);

    }

}