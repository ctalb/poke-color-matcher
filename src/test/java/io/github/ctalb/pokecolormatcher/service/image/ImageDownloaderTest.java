package io.github.ctalb.pokecolormatcher.service.image;

import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.jupiter.api.Assertions.*;

class ImageDownloaderTest {

    @Test
    void givenRealUrl_whenDownloadImage_thenReturnBytes() {
        ImageDownloader imageDownloader = new ImageDownloader();
        byte[] actual = imageDownloader.downloadImage("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png");

        assertNotNull(actual);
        assertTrue(actual.length > 0);
    }

    @Test
    void givenFakeImage_whenDownloadImage_thenReturnCorrectBytes() throws Exception {
        MockWebServer server = new MockWebServer();

        byte [] expected = {1, 2, 3, 4, 5};

        server.enqueue(new MockResponse()
                .setBody(new okio.Buffer().write(expected))
                .addHeader("Content-Type", "image/png"));

        server.start();
        String url = server.url("/test.png").toString();

        ImageDownloader imageDownloader = new ImageDownloader();
        byte[] actual = imageDownloader.downloadImage(url);

        assertArrayEquals(expected, actual);
    }
}