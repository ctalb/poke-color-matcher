package io.github.ctalb.pokecolormatcher.service.image;

import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class ImageService {
    private final ImageDownloader imageDownloader;
    private final ImageSaver imageSaver;

    public ImageService(ImageDownloader imageDownloader, ImageSaver imageSaver) {
        this.imageDownloader = imageDownloader;
        this.imageSaver = imageSaver;
    }

    public Path getSavedImage(String pokemonName) {
        return null;
    }

    public Path downloadAndSaveImage(String pokemonName, String spriteUrl) {
        byte[] imageData = imageDownloader.downloadImage(spriteUrl);
        return imageSaver.saveImage(pokemonName,imageData);
    }
}
