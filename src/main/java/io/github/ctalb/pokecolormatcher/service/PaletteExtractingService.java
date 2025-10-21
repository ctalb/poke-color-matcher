package io.github.ctalb.pokecolormatcher.service;

import de.androidpit.colorthief.ColorThief;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.io.IOException;

// Uses Color Thief library by Sven Woltmann
// https://github.com/SvenWoltmann/color-thief-java

@Service
public class PaletteExtractingService {

    public int [][] extractPalette(Path imagePath, int colorCount) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(imagePath.toFile());

        return ColorThief.getPalette(bufferedImage, colorCount);

    }

    public int getColorCount(Path imagePath, int maxCount) throws IOException {
        // TODO: Finish later
        return 0;
    }

}
