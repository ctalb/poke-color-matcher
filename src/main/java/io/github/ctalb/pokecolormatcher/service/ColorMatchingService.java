package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.DmcFloss;
import io.github.ctalb.pokecolormatcher.model.FlossColorMatch;
import io.github.ctalb.pokecolormatcher.util.ColorMath;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ColorMatchingService {
    private List<DmcFloss> flossList;

    public ColorMatchingService(List<DmcFloss> flossList) {
        this.flossList = flossList;
    }

    // Matches each color in the extracted palette to the closest matching DMC floss color
    public List<FlossColorMatch> matchPaletteToFlosses(int [][] extractedPalette) {

        // Holds the extracted color/floss match pairings
        List<FlossColorMatch> flossMatches = new ArrayList<>();

        for (int[] color : extractedPalette) {

            // Find the closest matching DMC floss for the given RGB color
            DmcFloss closestMatch = findFlossMatch(color);

            // Create an object pairing the extracted color with the floss match
            FlossColorMatch match = new FlossColorMatch(color, closestMatch);
            flossMatches.add(match);
        }

        return flossMatches;
    }

    // Finds the closest matching floss color for a given RGB color using Delta E (CIE76) difference
    private DmcFloss findFlossMatch(int [] colorRgb) {
        double[] extractedLab = ColorMath.rgbToLab(colorRgb);
        double smallestDifference = Double.MAX_VALUE;
        DmcFloss closestMatch = null;

        for (DmcFloss floss : flossList) {
            double[] flossLab = ColorMath.hexToLab(floss.hex());
            double difference = ColorMath.deltaE(extractedLab, flossLab);

            if (difference < smallestDifference) {
                smallestDifference = difference;
                closestMatch = floss;
            }
        }
        return closestMatch;
    }
}
