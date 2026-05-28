package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.DmcFloss;
import io.github.ctalb.pokecolormatcher.model.FlossColorMatch;
import io.github.ctalb.pokecolormatcher.util.ColorMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service responsible for matching a Pokémon's extracted color palette to the closest DMC embroidery floss colors.
 */
@Service
public class ColorMatchingService {

    private final List<DmcFloss> flossList;

    @Autowired
    public ColorMatchingService(List<DmcFloss> flossList) {
        this.flossList = flossList;
    }

    /**
     * Matches each color in the extracted palette to the closest matching DMC floss color.
     * @param extractedPalette a 2D array where each element is an RGB value extracted from the Pokémon's image.
     * @return a list of FlossColorMatch objects pairing each extracted color with its closest floss match.
     */
    public List<FlossColorMatch> matchPaletteToFlosses(int [][] extractedPalette) {

        List<FlossColorMatch> flossMatches = new ArrayList<>();
        // Tracks already-matched flosses to skip duplicates, which can occur when similar colors match to the same floss
        Set<String> seenFlosses = new HashSet<>();

        for (int[] color : extractedPalette) {

            DmcFloss closestMatch = findFlossMatch(color);

            if (!seenFlosses.contains(closestMatch.number())) {

                seenFlosses.add(closestMatch.number());

                FlossColorMatch match = new FlossColorMatch(color, closestMatch);
                flossMatches.add(match);
            }

        }

        return flossMatches;
    }

    /**
     * Finds the closest matching floss color for a given RGB color using Delta E (CIE76) difference.
     * @param colorRgb an array of red, green, and blue values representing an extracted color.
     * @return a DMCFloss object that most closely matches the extracted color.
     */
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
