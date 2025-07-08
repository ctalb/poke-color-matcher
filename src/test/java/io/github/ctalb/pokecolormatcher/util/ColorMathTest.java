package io.github.ctalb.pokecolormatcher.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorMathTest {

    private void assertHexToRgb(String hex, int[] expected) {
        assertArrayEquals(expected, ColorMath.hexToRgb(hex));
    }

    private void assertInvalidHex(String hex) {
        assertThrows(IllegalArgumentException.class, () -> ColorMath.hexToRgb(hex));
    }

    private void assertRgbToXyz(int[] rgb, double[] expected, double delta) {
        assertArrayEquals(expected, ColorMath.rgbToXyz(rgb), delta);
    }

    @Test
    void hexToRgbWithHash() {
        assertHexToRgb("#000000", new int[] {0, 0, 0}); // black
        assertHexToRgb("#ffffff", new int[] {255, 255, 255}); // white
        assertHexToRgb("#FF0000", new int[] {255, 0, 0}); // red
        assertHexToRgb("#00ff00", new int[] {0, 255, 0}); // green
        assertHexToRgb("#0000FF", new int[] {0, 0, 255}); // blue
        assertHexToRgb("#f1adff", new int[] {241, 173, 255}); // lilac
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
    void hexToRgbInvalidHex() {
        assertInvalidHex("#123");
        assertInvalidHex("#GGGGGG");
        assertInvalidHex("#FFFFF");
    }

    @Test
    void rgbToXyzTest() {
        assertRgbToXyz(new int[] {0, 0, 0}, new double[] {0.0000, 0.0000, 0.0000}, 0.001);
        assertRgbToXyz(new int[] {255, 255, 255}, new double[] {95.0500, 100.0000, 108.9000}, 0.001);
        assertRgbToXyz(new int[] {255, 0, 0}, new double[] {41.2400, 21.2600, 1.9300}, 0.001);
        assertRgbToXyz(new int[] {0, 255, 0}, new double[] {35.7600, 71.5200, 11.9200}, 0.001);
        assertRgbToXyz(new int[] {0, 0, 255}, new double[] {18.0500, 7.2200, 95.0500}, 0.001);
        assertRgbToXyz(new int[] {241, 173, 255}, new double[] {69.2692, 55.8079, 101.7289}, 0.001);
    }

}