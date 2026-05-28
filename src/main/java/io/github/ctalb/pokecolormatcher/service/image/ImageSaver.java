package io.github.ctalb.pokecolormatcher.service.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service responsible for saving Pokémon image data to the configured images folder.
 */
@Service
public class ImageSaver {

    private final String folder;

    /**
     * @param folder the directory path where Pokémon images are saved, injected from application properties.
     */
    public ImageSaver(@Value("${images.folder}") String folder) {
        this.folder = folder;
    }

    /**
     * Saves the Pokémon image.
     * @param pokemonName the Pokémon name, used to generate the image's file name.
     * @param imageData the image data as a byte array.
     * @return the path to the saved image file.
     * @throws RuntimeException if the image fails to save.
     */
    public Path saveImage(String pokemonName, byte [] imageData) {
        try {
            String fileName = pokemonName + "_default.png";
            Path directory = Paths.get(folder);
            Files.createDirectories(directory);
            Path filePath = directory.resolve(fileName);
            Files.write(filePath, imageData);
            return filePath;

        } catch (IOException e) {

            throw new RuntimeException("Failed to save image: " + e.getMessage(), e);
        }
    }
}
