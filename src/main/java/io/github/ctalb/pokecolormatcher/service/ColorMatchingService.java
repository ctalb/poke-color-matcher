package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.DmcFloss;
import io.github.ctalb.pokecolormatcher.model.FlossColorMatch;
import io.github.ctalb.pokecolormatcher.util.ColorMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColorMatchingService {

    private final List<DmcFloss> flossList;

    @Autowired
    public ColorMatchingService(List<DmcFloss> flossList) {
        this.flossList = flossList;
    }

    // Matches each color in the extracted palette to the closest matching DMC floss color
    public List<FlossColorMatch> matchPaletteToFlosses(int [][] extractedPalette) {

        List<FlossColorMatch> flossMatches = new ArrayList<>();

        for (int[] color : extractedPalette) {

            DmcFloss closestMatch = findFlossMatch(color);

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
            double currentDifference = ColorMath.deltaE(extractedLab, flossLab);

            if (currentDifference < smallestDifference) {
                smallestDifference = currentDifference;
                closestMatch = floss;
            }
        }
        return closestMatch;
    }
}
