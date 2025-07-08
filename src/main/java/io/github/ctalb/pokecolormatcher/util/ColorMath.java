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
}
