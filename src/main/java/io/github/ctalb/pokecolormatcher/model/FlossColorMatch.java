package io.github.ctalb.pokecolormatcher.model;

import java.util.Arrays;

public record FlossColorMatch(int [] extractedColor, DmcFloss match) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlossColorMatch that = (FlossColorMatch) o;
        return Arrays.equals(extractedColor, that.extractedColor) && match.equals(that.match);
    }
}
