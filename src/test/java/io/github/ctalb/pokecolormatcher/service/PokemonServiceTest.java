package io.github.ctalb.pokecolormatcher.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PokemonServiceTest {

    private MockWebServer server;
    private PokemonService pokemonService;
    String pokemonName;

    @BeforeEach
    void setup() throws IOException {

        this.server = new MockWebServer();
        server.start();

        String baseUrl = server.url("/").toString();
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        this.pokemonService = new PokemonService(webClient);

        pokemonName = "pikachu";
    }

    @AfterEach
    void teardown() throws IOException {
        server.close();
    }

    @Test
    void givenValidJson_whenGetSpriteDefaultUrl_returnCorrectUrl() throws InterruptedException {

        String jsonBody = """
                {
                "sprites": {
                    "front_default": "https://test.com/pikachu.png"
                    }
                }
                """;

        server.enqueue(new MockResponse()
                .setBody(jsonBody)
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json"));

        String expectedUrl = "https://test.com/pikachu.png";
        String actualUrl = pokemonService.getSpriteDefaultUrl(pokemonName);
        
        RecordedRequest recordedRequest = server.takeRequest();
        
        assertNotNull(actualUrl, "Actual URL should not be null");
        assertEquals(expectedUrl, actualUrl);
        assertEquals("/pokemon/pikachu", recordedRequest.getPath());
    }

    @Test
    void givenEmptySpritesKey_whenGetSpriteDefaultUrl_returnNull() throws InterruptedException {

        String jsonBody = """
                {
                "sprites": {}
                }
                """;

        server.enqueue(new MockResponse()
                .setBody(jsonBody)
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json"));

        String actualUrl = pokemonService.getSpriteDefaultUrl(pokemonName);
        assertNull(actualUrl, "Actual URL should be null");
    }

    @Test
    void givenMissingSpritesKey_whenGetSpriteDefaultUrl_returnNull() throws InterruptedException {

        String jsonBody = """
                {}
                """;

        server.enqueue(new MockResponse()
                .setBody(jsonBody)
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json"));

        String actualUrl = pokemonService.getSpriteDefaultUrl(pokemonName);
        assertNull(actualUrl, "Actual URL should be null");
    }
}