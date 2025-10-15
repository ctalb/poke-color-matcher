package io.github.ctalb.pokecolormatcher.service.image;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageSaver {

    public Path saveImage(String fileName, String destinationFolder, byte [] imageData) {
        try {

            Path directory = Paths.get(destinationFolder);
            Files.createDirectories(directory);
            Path filePath = directory.resolve(fileName);
            Files.write(filePath, imageData);
            return filePath;

        } catch (IOException e) {

            throw new RuntimeException("Failed to save image: " + e.getMessage(), e);
        }
    }
}
