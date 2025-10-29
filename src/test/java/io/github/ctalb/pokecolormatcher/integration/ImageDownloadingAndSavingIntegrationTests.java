package io.github.ctalb.pokecolormatcher.integration;

import io.github.ctalb.pokecolormatcher.service.image.ImageDownloader;
import io.github.ctalb.pokecolormatcher.service.image.ImageSaver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ImageDownloadingAndSavingIntegrationTests {

    @TempDir
    Path tempDir;

    private ImageDownloader downloader;
    private ImageSaver saver;
    private String pokemonName;
    private String url;
    private byte[] downloadedBytes;
    private byte[] savedBytes;
    Path savedPath;


    @BeforeEach
    void setUp() {
        this.pokemonName = "test";
        this.downloader = new ImageDownloader();
        this.saver = new ImageSaver(tempDir.toString());

    }

    @Test
    void givenFakeUrl_whenDownloadAndSaveImage_thenBytesMatch() throws IOException {

        MockWebServer server = new MockWebServer();

        byte[] fakeImage = new byte[]{1, 2, 3, 4, 5};

        server.enqueue(new MockResponse()
                .setBody(new okio.Buffer().write(fakeImage))
                .addHeader("Content-Type", "image/png"));

        url = server.url("/test.png").toString();
        downloadedBytes = downloader.downloadImage(url);

        server.close();

        saver.saveImage(pokemonName, downloadedBytes);
        savedPath = tempDir.resolve(pokemonName + "_default.png");
        savedBytes = Files.readAllBytes(savedPath);

        assertTrue(Files.exists(savedPath));
        assertArrayEquals(downloadedBytes, savedBytes);
    }
}
