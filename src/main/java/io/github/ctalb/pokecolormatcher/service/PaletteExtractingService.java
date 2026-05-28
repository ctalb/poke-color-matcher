package io.github.ctalb.pokecolormatcher.service;

import de.androidpit.colorthief.ColorThief;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Service responsible for extracting the color palette from the Pokémon image.
 * Uses the <a href="https://github.com/SvenWoltmann/color-thief-java">Color Thief</a> library by Sven Woltmann.
 * Licensed under the <a href="http://creativecommons.org/licenses/by/2.5/">Creative Commons Attribution 2.5 License</a>.
 */
@Service
public class PaletteExtractingService {

    /**
     * Extracts the color palette from the image.
     * @param imagePath the path to the Pokémon image.
     * @param colorCount the number of colors in the palette.
     * @return a 2D array where each element is an RGB value extracted from the image.
     * @throws IOException if the image file cannot be read.
     */
    public int [][] extractPalette(Path imagePath, int colorCount) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(imagePath.toFile());

        // Pixel sampling rate: 1 samples every pixel (highest quality, slowest), 10 samples every 10th pixel
        int QUALITY = 5;
        boolean IGNORE_WHITE = false;

        return ColorThief.getPalette(bufferedImage, colorCount, QUALITY, IGNORE_WHITE);

    }

    /**
     * Counts the number of unique colors in the Pokémon image.
     * @param imagePath the path to the image.
     * @param maxCount the maximum number of colors to return, used to cap the palette size for color extraction.
     * @return the number of unique colors.
     * @throws IOException if the image file cannot be read.
     */
    public int getColorCount(Path imagePath, int maxCount) throws IOException {

        BufferedImage image = ImageIO.read(imagePath.toFile());
        Set<Integer> uniqueColors = new HashSet<>();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                // Remove alpha byte
                int rgb = image.getRGB(x, y) & 0xFFFFFF;
                uniqueColors.add(rgb);
            }
        }
        return Math.min(uniqueColors.size(), maxCount);
    }

}
