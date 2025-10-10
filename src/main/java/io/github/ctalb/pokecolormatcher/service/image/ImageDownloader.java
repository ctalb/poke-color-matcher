package io.github.ctalb.pokecolormatcher.service.image;

import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;

@Service
public class ImageDownloader {

    public byte[] downloadImage(String url) {
        try (BufferedInputStream in = new BufferedInputStream(URI.create(url).toURL().openStream())) {
            return in.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to download image from " + url, e);
        }
    }
}
