package io.github.ctalb.pokecolormatcher.util;

/**
 * Utility class for color space conversions and color difference calculations.
 * Conversion math sourced from <a href="https://www.easyrgb.com/en/math.php">EasyRGB</a>.
 */

public class ColorMath {

    /**
     * Converts a hexadecimal value to its RGB equivalent.
     * @param hex a hexadecimal string.
     * @return an array of red, green, and blue values.
     * @throws IllegalArgumentException if the hexadecimal string is invalid.
     */
    public static int[] hexToRgb(String hex) {
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        if (hex.length() != 6) {
            throw new IllegalArgumentException("Invalid hexadecimal string: " + hex);
        }

        int red = Integer.parseInt(hex.substring(0, 2), 16);
        int green = Integer.parseInt(hex.substring(2, 4), 16);
        int blue = Integer.parseInt(hex.substring(4, 6), 16);
        return new int[]{red, green, blue};
    }

    /**
     * Converts an RGB value to its XYZ equivalent.
     * XYZ is a color space used as an intermediate step in color conversions.
     * @param rgb an array of red, green, and blue values.
     * @return an array of XYZ values.
     */
    public static double[] rgbToXyz(int[] rgb) {
        double red = (double)rgb[0] / 255;
        double green = (double)rgb[1] / 255;
        double blue = (double)rgb[2] / 255;

        if (red > 0.04045) {
            red = Math.pow((red + 0.055) / 1.055, 2.4);
        } else {
            red = red / 12.92;
        }
        if (green > 0.04045) {
            green = Math.pow((green + 0.055) / 1.055, 2.4);
        } else {
            green = green / 12.92;
        }
        if (blue > 0.04045) {
            blue = Math.pow((blue + 0.055) / 1.055, 2.4);
        } else {
            blue = blue / 12.92;
        }

        red *= 100;
        green *= 100;
        blue *= 100;

        double x = (red * 0.4124) + (green * 0.3576) + (blue * 0.1805);
        double y = (red * 0.2126) + (green * 0.7152) + (blue * 0.0722);
        double z = (red * 0.0193) + (green * 0.1192) + (blue * 0.9505);

        return new double[]{x, y, z};
    }

    /**
     * Converts an XYZ value to its LAB equivalent.
     * CIELAB is a perceptually uniform color space where numerical differences correspond to similar perceived color differences.
     * @param xyz an array of XYZ values.
     * @return an array of LAB values.
     */
    public static double[] xyzToLab(double[] xyz) {
        // D65 2° reference values
        double referenceX = 95.047;
        double referenceY = 100.000;
        double referenceZ = 108.883;

        double x = xyz[0] / referenceX;
        double y = xyz[1] / referenceY;
        double z = xyz[2] / referenceZ;

        if (x > 0.008856) {
            x = Math.pow(x, 1.0/3.0);
        } else {
            x = (7.787 * x) + (16.0/116.0);
        }
        if (y > 0.008856) {
            y = Math.pow(y, 1.0/3.0);
        } else {
            y = (7.787 * y) + (16.0/116.0);
        }
        if (z > 0.008856) {
            z = Math.pow(z, 1.0/3.0);
        } else {
            z = (7.787 * z) + (16.0/116.0);
        }

        double cieL = (116.0 * y) - 16.0;
        double cieA = 500.0 * (x - y);
        double cieB = 200.0 * (y - z);

        return new double[]{cieL, cieA, cieB};
    }

    /**
     * Converts a hexadecimal color to its LAB equivalent via RGB and XYZ conversions.
     * @param hex a hexadecimal string.
     * @return an array of LAB values.
     */
    public static double[] hexToLab(String hex) {
        int[] rgb = hexToRgb(hex);
        double[] xyz = rgbToXyz(rgb);
        return xyzToLab(xyz);
    }

    /**
     * Converts an RGB color to its LAB equivalent via XYZ conversion.
     * @param rgb an array of red, green, and blue values.
     * @return an array of LAB values.
     */
    public static double[] rgbToLab(int[] rgb) {
        double[] xyz = rgbToXyz(rgb);
        return xyzToLab(xyz);
    }

    /**
     * Calculates the color difference between two LAB colors using the CIE76 formula,
     * A perceptual color difference formula that measures color difference as perceived by the human eye.
     * @param lab1 an array of LAB values for the first color.
     * @param lab2 an array of LAB values for the second color.
     * @return the color difference value.
     */
    public static double deltaE(double[] lab1, double[] lab2) {
        double deltaL = lab1[0] - lab2[0];
        double deltaA = lab1[1] - lab2[1];
        double deltaB = lab1[2] - lab2[2];
        return Math.sqrt((deltaL * deltaL) + (deltaA * deltaA) + (deltaB * deltaB));
    }
}
