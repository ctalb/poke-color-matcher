package io.github.ctalb.pokecolormatcher.service;

import de.androidpit.colorthief.ColorThief;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

// Uses Color Thief library by Sven Woltmann
// https://github.com/SvenWoltmann/color-thief-java

@Service
public class PaletteExtractingService {

    public int [][] extractPalette(Path imagePath, int colorCount) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(imagePath.toFile());

        return ColorThief.getPalette(bufferedImage, colorCount);

    }

    public int getColorCount(Path imagePath, int maxCount) throws IOException {

        BufferedImage image = ImageIO.read(imagePath.toFile());
        Set<Integer> uniqueColors = new HashSet<>();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y) & 0xFFFFFF;
                uniqueColors.add(rgb);
            }
        }
        return Math.min(uniqueColors.size(), maxCount);
    }

}
