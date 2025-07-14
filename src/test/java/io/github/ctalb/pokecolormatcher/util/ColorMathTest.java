package io.github.ctalb.pokecolormatcher.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ColorMathTest {

    @ParameterizedTest
    @CsvSource({
            // black
            "#000000," +
                    "0, 0, 0",

            // white
            "#ffffff," +
                    "255, 255, 255",

            // red
            "#FF0000," +
                    "255, 0, 0",

            // green
            "#00ff00," +
                    "0, 255, 0",

            // blue
            "#0000FF," +
                    "0, 0, 255",

            // lilac
            "#f1adff," +
                    "241, 173, 255"
    })
    void hexToRgbWithHash(String hex, int r, int g, int b) {
        int[] expected = {r, g, b};
        int[] actual = ColorMath.hexToRgb(hex);
        assertArrayEquals(expected, actual, "Failed on: " + hex);
    }

    @ParameterizedTest
    @CsvSource({
            // black
            "000000," +
                    "0, 0, 0",

            // white
            "ffffff," +
                    "255, 255, 255",

            // red
            "FF0000," +
                    "255, 0, 0",

            // green
            "00ff00," +
                    "0, 255, 0",

            // blue
            "0000FF," +
                    "0, 0, 255",

            // lilac
            "f1adff," +
                    "241, 173, 255"
    })
    void hexToRgbWithOutHash(String hex, int r, int g, int b) {
        int[] expected = {r, g, b};
        int[] actual = ColorMath.hexToRgb(hex);
        assertArrayEquals(expected, actual, "Failed on: " + hex);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            // too short
            "#12345",

            // too long
            "#1234567",

            // all invalid chars
            "#GGGGGG",

            // one invalid char
            "#12345G",

            // empty
            ""
    })
    void hexToRgbInvalidHex(String invalidHex) {
        assertThrows(IllegalArgumentException.class, () -> ColorMath.hexToRgb(invalidHex),
                "Failed on: " + invalidHex);
    }

    @ParameterizedTest
    @CsvSource({
            // black
            "0, 0, 0," +
                    "0.0, 0.0, 0.0",

            // white
            "255, 255, 255," +
                    "95.05, 100.0, 108.8999999",

            // red
            "255, 0, 0," +
                    "41.24, 21.26, 1.9300000000000002",

            // green
            "0, 255, 0," +
                    "35.76, 71.52, 11.92",

            // blue
            "0, 0, 255," +
                    "18.05, 7.22, 95.05",

            // lilac
            "241, 173, 255," +
                    "69.26919778118356, 55.807912424894084, 101.72886127050332"
    })
    void rgbToXyzTest(int r, int g, int b, double x, double y, double z) {
        int[] rgb = {r, g, b};
        double[] expected = {x, y, z};
        double[] actual = ColorMath.rgbToXyz(rgb);
        assertArrayEquals(expected, actual, 1.0E-4, "Failed on: " + Arrays.toString(rgb));
    }

    @ParameterizedTest
    @CsvSource({
            // black
            "0.0, 0.0, 0.0," +
                    "0.00, 0.00, 0.00",

            //white
            "95.05, 100.0, 108.8999999," +
                    "100.0, 0.00526049995830391, -0.010408184525267927",

            // red
            "41.24, 21.26, 1.9300000000000002," +
                    "53.23288178584245, 80.10930952982204, 67.22006831026425",

            // green
            "35.76, 71.52, 11.92," +
                    "87.73703347354422, -86.18463649762525, 83.18116474777854",

            // blue
            "18.05, 7.22, 95.05," +
                    "32.302586667249486, 79.19666178930935, -107.86368104495168",

            // lilac
            "69.26919778118356, 55.807912424894084, 101.72886127050332, " +
                    "79.50437094195044, 38.29978656518518, -30.857370440765642"
    })
    void xyzToLabTest(double x, double y, double z, double l, double a, double b) {
        double[] xyz = {x, y, z};
        double[] expected = {l, a, b};
        double[] actual = ColorMath.xyzToLab(xyz);
        assertArrayEquals(expected, actual, 1.0E-4, "Failed on: " + Arrays.toString(xyz));
    }

    @ParameterizedTest
    @CsvSource({
            // black
            "#000000," +
                    "0.00, 0.00, 0.00",

            // white
            "#ffffff," +
                    "100.0, 0.00526049995830391, -0.010408184525267927",

            // red
            "#FF0000," +
                    "53.23288178584245, 80.10930952982204, 67.22006831026425",

            // green
            "#00ff00," +
                    "87.73703347354422, -86.18463649762525, 83.18116474777854",

            // blue
            "#0000FF," +
                    "32.302586667249486, 79.19666178930935, -107.86368104495168",

            // lilac
            "#f1adff," +
                    "79.50437094195044, 38.29978656518518, -30.857370440765642"
    })
    void hexToLabTest(String hex, double l, double a, double b) {
        double[] expected = {l, a, b};
        double[] actual = ColorMath.hexToLab(hex);
        assertArrayEquals(expected, actual, 1.0E-4, "Failed on: " + Arrays.toString(expected));
    }

    @ParameterizedTest
    @CsvSource({
            // black (#000000) and white (#FFFFFF)
            "0.00, 0.00, 0.00," +
                    "100.0, 0.00526049995830391, -0.010408184525267927," +
                    " 100.0",

            // red (#FF0000) and green (#00FF00)
            "53.23288178584245, 80.10930952982204, 67.22006831026425," +
                    "87.73703347354422, -86.18463649762525, 83.18116474777854," +
                    "170.5842",

            // blue (#0000FF) and lilac (#F1ADFF)
            "32.302586667249486, 79.19666178930935, -107.86368104495168," +
                    "79.50437094195044, 38.29978656518518, -30.857370440765642," +
                    "99.1491",

            // orange (#FF7F27) and golden yellow (#FFCA18)
            "66.96467979378511, 43.84463891440332, 65.3278424501983," +
                    "83.73981951242328, 4.940206156840987, 82.21867519388073," +
                    "45.6099"

    })
    void deltaETest(double l1, double a1, double b1, double l2, double a2, double b2, double expected) {
        double[] lab1 = {l1, a1, b1};
        double [] lab2 = {l2, a2, b2};
        double actual = ColorMath.deltaE(lab1, lab2);
        assertEquals(expected, actual, 1.0E-4, "Failed on: " + Arrays.toString(lab1) + " and " + Arrays.toString(lab2));
    }

}