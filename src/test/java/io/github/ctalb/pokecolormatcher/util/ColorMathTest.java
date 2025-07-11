package io.github.ctalb.pokecolormatcher.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ColorMathTest {

    // 1.0E-4

    @ParameterizedTest
    @CsvSource({
            "#000000, 0, 0, 0", // black
            "#ffffff, 255, 255, 255", // white
            "#FF0000, 255, 0, 0", // red
            "#00ff00, 0, 255, 0", // green
            "#0000FF, 0, 0, 255", // blue
            "#f1adff, 241, 173, 255" // lilac
    })
    void hexToRgbWithHash(String hex, int r, int g, int b) {
        int[] expected = {r, g, b};
        int[] actual = ColorMath.hexToRgb(hex);
        assertArrayEquals(expected, actual, "Failed on: " + hex);
    }

    @ParameterizedTest
    @CsvSource({
            "000000, 0, 0, 0", // black
            "ffffff, 255, 255, 255", // white
            "FF0000, 255, 0, 0", // red
            "00ff00, 0, 255, 0", // green
            "0000FF, 0, 0, 255", // blue
            "f1adff, 241, 173, 255" // lilac
    })
    void hexToRgbWithOutHash(String hex, int r, int g, int b) {
        int[] expected = {r, g, b};
        int[] actual = ColorMath.hexToRgb(hex);
        assertArrayEquals(expected, actual, "Failed on: " + hex);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "#12345", // too short
            "#1234567", // too long
            "#GGGGGG", // all invalid chars
            "#12345G", // one invalid char
            "" // empty
    })
    void hexToRgbInvalidHex(String invalidHex) {
        assertThrows(IllegalArgumentException.class, () -> ColorMath.hexToRgb(invalidHex),
                "Failed on: " + invalidHex);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 0.0, 0.0, 0.0", // black
            "255, 255, 255, 95.05, 100.0, 108.8999999", // white
            "255, 0, 0, 41.24, 21.26, 1.9300000000000002", // red
            "0, 255, 0, 35.76, 71.52, 11.92", // gren
            "0, 0, 255, 18.05, 7.22, 95.05", // blue
            "241, 173, 255, 69.26919778118356, 55.807912424894084, 101.72886127050332" // lilac
    })
    void rgbToXyzTest(int r, int g, int b, double x, double y, double z) {
        int[] rgb = {r, g, b};
        double[] expected = {x, y, z};
        double[] actual = ColorMath.rgbToXyz(rgb);
        assertArrayEquals(expected, actual, 1.0E-4, "Failed on: " + Arrays.toString(rgb));
    }

    @ParameterizedTest
    @CsvSource({
            "0.0, 0.0, 0.0, 0.00, 0.00, 0.00", // black
            "95.05, 100.0, 108.8999999, 100.0, 0.00526049995830391, -0.010408184525267927", //white
            "41.24, 21.26, 1.9300000000000002, 53.23288178584245, 80.10930952982204, 67.22006831026425", // red
            "35.76, 71.52, 11.92, 87.73703347354422, -86.18463649762525, 83.18116474777854", // green
            "18.05, 7.22, 95.05, 32.302586667249486, 79.19666178930935, -107.86368104495168", // blue
            "69.26919778118356, 55.807912424894084, 101.72886127050332, " +
                    "79.50437094195044, 38.29978656518518, -30.857370440765642" // lilac
    })
    void xyzToLabTest(double x, double y, double z, double l, double a, double b) {
        double[] xyz = {x, y, z};
        double[] expected = {l, a, b};
        double[] actual = ColorMath.xyzToLab(xyz);
        assertArrayEquals(expected, actual, 1.0E-4, "Failed on: " + Arrays.toString(xyz));
    }

    @ParameterizedTest
    @CsvSource({
            "#000000, 0.00, 0.00, 0.00", // black
            "#ffffff, 100.0, 0.00526049995830391, -0.010408184525267927", // white
            "#FF0000, 53.23288178584245, 80.10930952982204, 67.22006831026425", // red
            "#00ff00, 87.73703347354422, -86.18463649762525, 83.18116474777854", // green
            "#0000FF, 32.302586667249486, 79.19666178930935, -107.86368104495168", // blue
            "#f1adff, 79.50437094195044, 38.29978656518518, -30.857370440765642" // lilac
    })
    void hexToLabTest(String hex, double l, double a, double b) {
        double[] expected = {l, a, b};
        double[] actual = ColorMath.hexToLab(hex);
        assertArrayEquals(expected, actual, 1.0E-4, "Failed on: " + Arrays.toString(expected));
    }

}