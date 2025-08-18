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

    public List<FlossColorMatch> matchPaletteToFlosses(int [][] extractedPalette) {
        //double[] extractedLab;
        //double[] flossLab;
        List<FlossColorMatch> flossMatches = new ArrayList<FlossColorMatch>();

        for (int[] color : extractedPalette) {
            DmcFloss closestMatch = findFlossMatch(color);
            //DmcFloss closestMatch = null;
            /*extractedLab = ColorMath.rgbToLab(color);
            double smallestDifference = Double.MAX_VALUE;

            for (DmcFloss floss : flossList) {
                flossLab = ColorMath.hexToLab(floss.hex());
                double deltaE = ColorMath.deltaE(extractedLab, flossLab);

                if (deltaE < smallestDifference) {
                    smallestDifference = deltaE;
                    closestMatch = floss;
                }
            }
            FlossColorMatch match = new FlossColorMatch(color, closestMatch);
            flossMatches.add(match);
            System.out.println("Input: " + Arrays.toString(match.extractedColor()));
            System.out.println("Match: " + match.match());*/
            FlossColorMatch match = new FlossColorMatch(color, closestMatch);
            flossMatches.add(match);
            System.out.println(match.toString());
        }

        return flossMatches;
    }

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
