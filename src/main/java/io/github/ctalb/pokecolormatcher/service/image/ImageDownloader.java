package io.github.ctalb.pokecolormatcher.service.image;

import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;

/**
 * Service responsible for downloading Pokémon images from a given URL.
 */
@Service
public class ImageDownloader {

    /**
     * Downloads the Pokémon image.
     * @param url the URL for the image.
     * @return the image data as a byte array.
     * @throws RuntimeException if the image download fails.
     */
    public byte[] downloadImage(String url) {
        try (BufferedInputStream in = new BufferedInputStream(URI.create(url).toURL().openStream())) {
            return in.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to download image from " + url, e);
        }
    }
}
