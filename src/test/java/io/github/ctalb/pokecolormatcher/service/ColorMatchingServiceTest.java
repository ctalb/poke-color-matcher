package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.DmcFloss;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColorMatchingServiceTest {

    @Test
    void getClosestMatchTest() {
        int[][] palette = new int[][] {
                {255, 0, 0},
                {0, 255, 0},
                {0, 0, 255},
        };
        List<DmcFloss> flossList = new ArrayList<>();
        DmcFloss floss = new DmcFloss("666", "Bright Red", "#e02d27");
        flossList.add(floss);
        floss = new DmcFloss("310", "Black", "#000000");
        flossList.add(floss);
        floss = new DmcFloss("703", "Chartreuse", "#83b247");
        flossList.add(floss);
        floss = new DmcFloss("798", "Dark Delft Blue", "#2763b0");
        flossList.add(floss);
        ColorMatchingService colorMatchingService = new ColorMatchingService(flossList);

        colorMatchingService.getClosestMatch(palette);
    }
}