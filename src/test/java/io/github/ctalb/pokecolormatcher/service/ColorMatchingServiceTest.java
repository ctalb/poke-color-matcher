package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.config.FlossConfig;
import io.github.ctalb.pokecolormatcher.model.DmcFloss;
import io.github.ctalb.pokecolormatcher.model.FlossColorMatch;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColorMatchingServiceTest {

    @Test
    void givenRgbPalette_givenCustomFlossList_whenMatchPaletteToFlosses_thenReturnRgbFlossColorMatchList() {
        int[][] palette = new int[][] {
                // Red, green, blue
                {255, 0, 0},
                {0, 255, 0},
                {0, 0, 255},
        };
        List<DmcFloss> flossList = List.of(
                new DmcFloss("666", "Bright Red", "#e02d27"),
                new DmcFloss("310", "Black", "#000000"),
                new DmcFloss("703", "Chartreuse", "#83b247"),
                new DmcFloss("798", "Dark Delft Blue", "#2763b0")
        );

        ColorMatchingService colorMatchingService = new ColorMatchingService(flossList);

        List<FlossColorMatch> expected = List.of(
                new FlossColorMatch(new int[]{255, 0, 0}, new DmcFloss("666", "Bright Red", "#e02d27")),
                new FlossColorMatch(new int[]{0, 255, 0},new DmcFloss("703", "Chartreuse", "#83b247")),
                new FlossColorMatch(new int[]{0, 0, 255}, new DmcFloss("798", "Dark Delft Blue", "#2763b0"))
        );

        List<FlossColorMatch> actual = colorMatchingService.matchPaletteToFlosses(palette);
        assertEquals(expected, actual);
    }

    @Test
    void givenOypPalette_givenCustomFlossList_whenMatchPaletteToFlosses_thenReturnOypFlossColorMatchList() {
        int[][] palette = new int[][] {
                // Orange, yellow, purple
                {255, 141, 62},
                {255, 242, 0},
                {184, 61, 186}
        };
        List<DmcFloss> flossList = List.of(
                new DmcFloss("B5200", "White", "#FFFFFF"),
                new DmcFloss("307", "Lemon", "#f7e204"),
                new DmcFloss("33", "Fuchsia", "#ad5c9a"),
                new DmcFloss("947", "Burnt Orange", "#f87917")
        );

        ColorMatchingService colorMatchingService = new ColorMatchingService(flossList);

        List<FlossColorMatch> expected = List.of(
                new FlossColorMatch(new int[]{255, 141, 62}, new DmcFloss("947", "Burnt Orange", "#f87917")),
                new FlossColorMatch(new int[]{255, 242, 0}, new DmcFloss("307", "Lemon", "#f7e204")),
                new FlossColorMatch(new int[]{184, 61, 186}, new DmcFloss("33", "Fuchsia", "#ad5c9a"))
        );

        List<FlossColorMatch> actual = colorMatchingService.matchPaletteToFlosses(palette);
        assertEquals(expected, actual);
    }

    @Test
    void givenBwPalette_givenFlossJson_whenMatchPaletteToFlosses_thenReturnBwFlossColorMatchList() throws IOException {

        int[][] palette = new int[][] {
                // white, black
                {0, 0, 0},
                {255, 255, 255}
        };

        FlossConfig flossConfig = new FlossConfig();
        List<DmcFloss> flossList = flossConfig.flossList();
        ColorMatchingService colorMatchingService = new ColorMatchingService(flossList);

        List<FlossColorMatch> expected = List.of(
                new FlossColorMatch(new int[]{0, 0, 0}, new DmcFloss("310", "Black", "#000000")),
                new FlossColorMatch(new int[]{255, 255, 255}, new DmcFloss("B5200", "White", "#ffffff"))
        );

        List<FlossColorMatch> actual = colorMatchingService.matchPaletteToFlosses(palette);
        assertEquals(expected, actual);
    }
}