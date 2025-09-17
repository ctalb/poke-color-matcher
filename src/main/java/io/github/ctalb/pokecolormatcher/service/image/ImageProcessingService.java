package io.github.ctalb.pokecolormatcher.service.image;

import org.springframework.stereotype.Service;

@Service
public class ImageProcessingService {

    private final ImageDownloader imageDownloader;
    private final BackgroundRemover backgroundRemover;
    private final ImageSaver imageSaver;

    public ImageProcessingService(ImageDownloader imageDownloader, BackgroundRemover backgroundRemover, ImageSaver imageSaver) {
        this.imageDownloader = imageDownloader;
        this.backgroundRemover = backgroundRemover;
        this.imageSaver = imageSaver;
    }
}
