package io.github.ctalb.pokecolormatcher.service.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ImageService {
    private final ImageDownloader imageDownloader;
    private final ImageSaver imageSaver;
    private final String folder;

    public ImageService(ImageDownloader imageDownloader, ImageSaver imageSaver,
                        @Value("${images.folder}") String folder) {
        this.imageDownloader = imageDownloader;
        this.imageSaver = imageSaver;
        this.folder = folder;
    }

    public Path getSavedImage(String pokemonName) {

        String imageName = pokemonName + "_default.png";
        Path directory = Path.of(folder);
        Path imagePath = directory.resolve(imageName);
        if (Files.exists(imagePath)) {
            return imagePath;
        }

        return null;
    }

    public Path downloadAndSaveImage(String pokemonName, String spriteUrl) {
        byte[] imageData = imageDownloader.downloadImage(spriteUrl);
        return imageSaver.saveImage(pokemonName,imageData);
    }
}
