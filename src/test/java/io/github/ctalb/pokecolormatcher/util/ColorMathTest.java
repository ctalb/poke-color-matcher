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

    private void assertXyzToLab(double[] xyz, double[] expected, double delta) {
        assertArrayEquals(expected, ColorMath.xyzToLab(xyz), delta);
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
        assertRgbToXyz(new int[] {0, 0, 0}, new double[] {0.0, 0.0, 0.0}, 0.0001);
        assertRgbToXyz(new int[] {255, 255, 255}, new double[] {95.05, 100.0, 108.8999999}, 0.0001);
        assertRgbToXyz(new int[] {255, 0, 0}, new double[] {41.24, 21.26, 1.9300000000000002}, 0.0001);
        assertRgbToXyz(new int[] {0, 255, 0}, new double[] {35.76, 71.52, 11.92}, 0.0001);
        assertRgbToXyz(new int[] {0, 0, 255}, new double[] {18.05, 7.22, 95.05}, 0.0001);
        assertRgbToXyz(new int[] {241, 173, 255},
                new double[] {69.26919778118356, 55.807912424894084, 101.72886127050332}, 0.0001);
    }

    @Test
    void xyzToLabTest() {
        assertXyzToLab(new double[] {0.0, 0.0, 0.0}, new double[] {0.00, 0.00, 0.00}, 0.0001);
        assertXyzToLab(new double[] {95.05, 100.0, 108.8999999},
                new double[] {100.0, 0.00526049995830391, -0.010408184525267927}, 0.0001);
        assertXyzToLab(new double[] {41.24, 21.26, 1.9300000000000002},
                new double[] {53.23288178584245, 80.10930952982204, 67.22006831026425}, 0.0001);
        assertXyzToLab(new double[] {35.76, 71.52, 11.92},
                new double[] {87.73703347354422, -86.18463649762525, 83.18116474777854}, 0.0001);
        assertXyzToLab(new double[] {18.05, 7.22, 95.05},
                new double[] {32.302586667249486, 79.19666178930935, -107.86368104495168}, 0.0001);
        assertXyzToLab(new double[] {69.26919778118356, 55.807912424894084, 101.72886127050332},
                new double[] {79.50437094195044, 38.29978656518518, -30.857370440765642}, 0.0001);
    }

}