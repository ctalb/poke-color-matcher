package io.github.ctalb.pokecolormatcher.service.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceTest {

    @TempDir
    Path tempDir;
    private ImageService imageService;
    private ImageDownloader downloader;
    private ImageSaver saver;
    private String pokemonName;
    private String url;
    Path savedPath;
    Path expectedPath;

    @BeforeEach
    void setUp() {
        this.downloader = new ImageDownloader();
        this.saver = new ImageSaver(tempDir.toString());
        this.imageService = new ImageService(downloader, saver, tempDir.toString());
        pokemonName = "test";
    }

    @Test
    void givenPokemonName_whenGetSavedImage_thenReturnCorrectPath() {

        savedPath = saver.saveImage(pokemonName, new byte[] {1, 2, 3, 4, 5});
        Path returnedPath = imageService.getSavedImage(pokemonName);

        assertEquals(savedPath, returnedPath);

    }

    @Test
    void givenFakeUrl_whenDownloadAndSaveImage_thenReturnCorrectPath() throws IOException {

        MockWebServer server = new MockWebServer();

        byte[] expectedBytes = new byte[]{1, 2, 3, 4, 5};

        server.enqueue(new MockResponse()
                .setBody(new okio.Buffer().write(expectedBytes))
                .addHeader("Content-Type", "image/png"));
        server.start();

        url = server.url("/test.png").toString();

        savedPath = imageService.downloadAndSaveImage(pokemonName, url);

        server.close();

        byte[] savedBytes = Files.readAllBytes(savedPath);
        expectedPath = tempDir.resolve(pokemonName + "_default.png");

        assertNotNull(savedPath, "Path should not be null");
        assertTrue(Files.exists(savedPath), "File does not exist");
        assertArrayEquals(expectedBytes, savedBytes, "Saved bytes do not match expected bytes");
        assertEquals(expectedPath, savedPath, "Saved path does not match expected path");

    }

    @Test
    void givenRealUrl_whenDownloadAndSaveImage_thenReturnCorrectPath() throws IOException {

        url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png";

        savedPath = imageService.downloadAndSaveImage(pokemonName, url);
        expectedPath = tempDir.resolve(pokemonName + "_default.png");

        byte[] downloadedBytes = downloader.downloadImage(url);
        byte[] savedBytes = Files.readAllBytes(savedPath);


        assertNotNull(savedPath, "Path should not be null");
        assertTrue(Files.exists(savedPath), "File does not exist");
        assertEquals(expectedPath, savedPath, "Saved path does not match expected path");
        assertArrayEquals(downloadedBytes, savedBytes, "Saved bytes do not match expected bytes");
    }

}