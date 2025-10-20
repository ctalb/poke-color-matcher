package io.github.ctalb.pokecolormatcher.service.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageSaver {

    private final String folder;

    public ImageSaver(@Value("${images.folder}") String folder) {
        this.folder = folder;
    }

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
