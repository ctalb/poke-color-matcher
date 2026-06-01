package io.github.ctalb.pokecolormatcher.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a match between an extracted RGB value and its associated DMC floss.
 */
public record FlossColorMatch(int [] extractedColor, DmcFloss match) {

    // Overridden manually because the default record implementation does not handle array equality correctly
    // for extractedColor
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlossColorMatch that = (FlossColorMatch) o;
        return Arrays.equals(extractedColor, that.extractedColor) && match.equals(that.match);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(match);
        result = 31 * result + Arrays.hashCode(extractedColor);
        return result;
    }
}
