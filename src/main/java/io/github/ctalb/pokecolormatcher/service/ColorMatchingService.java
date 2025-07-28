package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.DmcFloss;
import io.github.ctalb.pokecolormatcher.model.FlossColorMatch;
import io.github.ctalb.pokecolormatcher.util.ColorMath;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColorMatchingService {
    private List<DmcFloss> flossList;

    public ColorMatchingService(List<DmcFloss> flossList) {
        this.flossList = flossList;
    }

    public List<FlossColorMatch> getClosestMatch(int [][] extractedPalette) {
        double[] extractedLab;
        double[] flossLab;
        List<FlossColorMatch> flossMatches = new ArrayList<FlossColorMatch>();

        for (int[] color : extractedPalette) {
            System.out.println(color[0] + " " + color[1] + " " + color[2]);
            DmcFloss closestMatch = null;
            extractedLab = ColorMath.rgbToLab(color);
            double smallestDifference = Double.MAX_VALUE;

            for (DmcFloss floss : flossList) {
                flossLab = ColorMath.hexToLab(floss.hex());
                double deltaE = ColorMath.deltaE(extractedLab, flossLab);
                System.out.println(floss.number() + ": " + deltaE);

                if (deltaE < smallestDifference) {
                    smallestDifference = deltaE;
                    closestMatch = floss;
                }
            }
            System.out.println("Match: " + closestMatch);
            FlossColorMatch match = new FlossColorMatch(color, closestMatch);
            flossMatches.add(match);
        }
        return flossMatches;
    }
    
}
