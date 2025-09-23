package io.github.ctalb.pokecolormatcher.service.image;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageDownloaderTest {

    @Test
    void givenRealUrl_whenDownloadImage_thenReturnBytes() {
        ImageDownloader imageDownloader = new ImageDownloader();
        byte[] actual = imageDownloader.downloadImage("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png");

        assertNotNull(actual);
        assertTrue(actual.length > 0);
    }
}