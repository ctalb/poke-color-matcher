package io.github.ctalb.pokecolormatcher.service;

import de.androidpit.colorthief.ColorThief;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Uses Color Thief library by Sven Woltmann
// https://github.com/SvenWoltmann/color-thief-java

@Service
public class PaletteExtractingService {

    public int [][] extractPalette(File imageFile, int colorCount) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(imageFile);

        return ColorThief.getPalette(bufferedImage, colorCount);

    }

}
