package io.github.ctalb.pokecolormatcher.service.image;

import io.github.ctalb.pokecolormatcher.service.PokemonService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

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
