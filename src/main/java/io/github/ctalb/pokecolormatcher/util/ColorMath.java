package io.github.ctalb.pokecolormatcher.util;

public class ColorMath {

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

    public static double[] hexToLab(String hex) {
        int[] rgb = hexToRgb(hex);
        double[] xyz = rgbToXyz(rgb);
        return xyzToLab(xyz);
    }

    public static double[] rgbToLab(int[] rgb) {
        double[] xyz = rgbToXyz(rgb);
        return xyzToLab(xyz);
    }

    public static double deltaE(double[] lab1, double[] lab2) {
        // Calculate color distance
        // CIE76 formula
        double deltaL = lab1[0] - lab2[0];
        double deltaA = lab1[1] - lab2[1];
        double deltaB = lab1[2] - lab2[2];
        return Math.sqrt((deltaL * deltaL) + (deltaA * deltaA) + (deltaB * deltaB));
    }
}
