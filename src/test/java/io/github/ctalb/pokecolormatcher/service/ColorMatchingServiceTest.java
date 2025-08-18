package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.DmcFloss;
import io.github.ctalb.pokecolormatcher.model.FlossColorMatch;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColorMatchingServiceTest {

    @Test
    void matchPaletteToFlossesTest() {
        int[][] palette = new int[][] {
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
}