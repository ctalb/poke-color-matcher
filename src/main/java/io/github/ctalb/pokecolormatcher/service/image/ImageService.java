package io.github.ctalb.pokecolormatcher.service.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Service responsible for orchestrating image downloading, saving, and retrieving.
 */
@Service
public class ImageService {
    private final ImageDownloader imageDownloader;
    private final ImageSaver imageSaver;
    private final String folder;

    /**
     * @param folder the directory path where Pokémon images are saved, injected from application properties.
     */
    public ImageService(ImageDownloader imageDownloader, ImageSaver imageSaver,
                        @Value("${images.folder}") String folder) {
        this.imageDownloader = imageDownloader;
        this.imageSaver = imageSaver;
        this.folder = folder;
    }

    /**
     * Attempts to retrieve a saved Pokémon image.
     * @param pokemonName the Pokémon name, used to generate the image's file name.
     * @return the image path if the saved image exists, null otherwise.
     */
    public Path getSavedImage(String pokemonName) {

        String imageName = pokemonName + "_default.png";
        Path directory = Path.of(folder);
        Path imagePath = directory.resolve(imageName);
        if (Files.exists(imagePath)) {
            return imagePath;
        }

        return null;
    }

    /**
     * Downloads a Pokémon image from the given URL and saves it to the images folder.
     * @param pokemonName the Pokémon name, used to generate the image's file name.
     * @param spriteUrl the URL of the Pokémon sprite to download.
     * @return the path to the saved image file.
     */
    public Path downloadAndSaveImage(String pokemonName, String spriteUrl) {
        byte[] imageData = imageDownloader.downloadImage(spriteUrl);
        return imageSaver.saveImage(pokemonName,imageData);
    }
}
