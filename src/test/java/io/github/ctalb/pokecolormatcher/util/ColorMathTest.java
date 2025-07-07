package io.github.ctalb.pokecolormatcher.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorMathTest {

    private void assertHexToRgb(String hex, int[] expected) {
        assertArrayEquals(expected, ColorMath.hexToRgb(hex));
    }

    @Test
    void hexToRgbWithHash() {
        assertHexToRgb("#000000", new int[] {0, 0, 0});
        assertHexToRgb("#ffffff", new int[] {255, 255, 255});
        assertHexToRgb("#FF0000", new int[] {255, 0, 0});
        assertHexToRgb("#00ff00", new int[] {0, 255, 0});
        assertHexToRgb("#0000FF", new int[] {0, 0, 255});
        assertHexToRgb("#f1adff", new int[] {241, 173, 255});
    }

    @Test
    void hexToRgbWithOutHash() {
        assertHexToRgb("000000", new int[] {0, 0, 0});
        assertHexToRgb("ffffff", new int[] {255, 255, 255});
        assertHexToRgb("FF0000", new int[] {255, 0, 0});
        assertHexToRgb("00ff00", new int[] {0, 255, 0});
        assertHexToRgb("0000FF", new int[] {0, 0, 255});
        assertHexToRgb("f1adff", new int[] {241, 173, 255});
    }

    @Test
    void rgbToXyzTest() {
        int[] test = new int[] {184, 61, 186};
        ColorMath.rgbToXyz(test);
    }

}