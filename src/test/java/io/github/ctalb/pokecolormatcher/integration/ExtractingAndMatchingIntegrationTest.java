package io.github.ctalb.pokecolormatcher.integration;

import io.github.ctalb.pokecolormatcher.config.FlossConfig;
import io.github.ctalb.pokecolormatcher.model.DmcFloss;
import io.github.ctalb.pokecolormatcher.model.FlossColorMatch;
import io.github.ctalb.pokecolormatcher.service.ColorMatchingService;
import io.github.ctalb.pokecolormatcher.service.PaletteExtractingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.internal.matchers.ArrayEquals;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExtractingAndMatchingIntegrationTest {

    @TempDir
    Path tempDir;

    private PaletteExtractingService paletteExtractingService;
    private ColorMatchingService colorMatchingService;
    private Path testImageRgbPath;
    private Path testImageBwPath;
    private List<DmcFloss> customFlossList;
    private FlossConfig flossConfig;

    @BeforeEach
    public void setup() throws IOException {

        paletteExtractingService = new PaletteExtractingService();

    }

    @Test
    void givenRgbImage_givenCustomFlossList_whenExtractAndMatch_thenReturnExpectedFlossColorMatch() throws IOException {

        Path imageRgb = createImageRgb();

        int colorCount = paletteExtractingService.getColorCount(imageRgb, 10);
        int[][] palette = paletteExtractingService.extractPalette(imageRgb, colorCount);

        customFlossList = createCustomFlossList();
        colorMatchingService = new ColorMatchingService(customFlossList);

        List<FlossColorMatch> actualList = colorMatchingService.matchPaletteToFlosses(palette);

        List<FlossColorMatch> expectedList = List.of(
                new FlossColorMatch(new int[]{255, 0, 0}, new DmcFloss("666", "Bright Red", "#e02d27")),
                new FlossColorMatch(new int[]{0, 255, 0},new DmcFloss("703", "Chartreuse", "#83b247")),
                new FlossColorMatch(new int[]{0, 0, 255}, new DmcFloss("798", "Dark Delft Blue", "#2763b0"))
        );

        for (FlossColorMatch expected: expectedList) {
            boolean matchFound = false;
            for (FlossColorMatch actual : actualList) {
                if (colorsAlmostEqual(actual.extractedColor(), expected.extractedColor(), 10)) {
                    assertEquals(expected.match(), actual.match(),
                            "Failed on: " + expected.match().number() + " (expected) vs. " +
                                    actual.match().number() + " (actual)");

                    matchFound = true;
                    break;
                }
            }
            if (!matchFound) {
                fail("No match found for expected floss: " + expected.match().number());
            }
        }

    }

    private Path createImageRgb()  throws IOException {

        BufferedImage testImageRGB = new BufferedImage(100, 90, BufferedImage.TYPE_INT_RGB);

        Graphics g = testImageRGB.createGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, 100, 30);
        g.setColor(Color.GREEN);
        g.fillRect(0, 30, 100, 30);
        g.setColor(Color.BLUE);
        g.fillRect(0, 60, 100, 30);
        g.dispose();

        testImageRgbPath = tempDir.resolve("testRGB.png");
        ImageIO.write(testImageRGB, "png", testImageRgbPath.toFile());

        return testImageRgbPath;
    }

    private Path createImageBw()   throws IOException {

        BufferedImage testImageBw = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        Graphics g = testImageBw.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 100, 50);
        g.setColor(Color.WHITE);
        g.fillRect(0, 50, 100, 50);
        g.dispose();

        testImageBwPath = tempDir.resolve("testBw.png");
        ImageIO.write(testImageBw, "png", testImageBwPath.toFile());

        return testImageBwPath;
    }

    private List<DmcFloss> createCustomFlossList() {
        return customFlossList = List.of(
                new DmcFloss("666", "Bright Red", "#e02d27"),
                new DmcFloss("310", "Black", "#000000"),
                new DmcFloss("703", "Chartreuse", "#83b247"),
                new DmcFloss("798", "Dark Delft Blue", "#2763b0"),
                new DmcFloss("B5200", "White", "#FFFFFF"),
                new DmcFloss("307", "Lemon", "#f7e204"),
                new DmcFloss("33", "Fuchsia", "#ad5c9a"),
                new DmcFloss("947", "Burnt Orange", "#f87917")
        );
    }

    private boolean colorsAlmostEqual(int[] actual, int[] expected, int delta) {
        return Math.abs(actual[0] - expected[0]) <= delta  &&
                Math.abs(actual[1] - expected[1]) <= delta &&
                Math.abs(actual[2] - expected[2]) <= delta;
    }



}
