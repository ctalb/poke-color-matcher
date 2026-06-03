package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.DmcFloss;
import io.github.ctalb.pokecolormatcher.model.FlossColorMatch;
import io.github.ctalb.pokecolormatcher.model.PokemonMatchResult;
import io.github.ctalb.pokecolormatcher.service.image.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PokemonMatchServiceTest {

    @Mock
    private PokemonService pokemonService;
    @Mock
    private ImageService imageService;
    @Mock
    private PaletteExtractingService paletteExtractingService;
    @Mock
    private ColorMatchingService colorMatchingService;
    @Mock
    private ResultStorageService resultStorageService;

    @InjectMocks
    private PokemonMatchService pokemonMatchService;

    private final String pokemonName = "pikachu";
    private final Path imagePath = Path.of("images", pokemonName + "_default.png");

    @Test
    void givenNoExistingResult_givenNoSavedImage_whenGetMatchResult_thenWorkflowExecutesCorrectly() throws Exception {

        int colorCount = 2;
        int[][] dummyPalette = new int[][]{{0, 0, 0}, {255, 255, 255}};
        List<FlossColorMatch> dummyMatches = List.of(
                new FlossColorMatch(new int[]{0, 0, 0}, new DmcFloss("310", "Black", "#000000")),
                new FlossColorMatch(new int[]{255, 255, 255}, new DmcFloss("B5200", "White", "#ffffff"))
        );

        when(resultStorageService.readResult(pokemonName)).thenReturn(null);
        when(imageService.getSavedImage(pokemonName)).thenReturn(null);
        when(pokemonService.getSpriteDefaultUrl(pokemonName)).thenReturn("https://test.com/pikachu.png");
        when(imageService.downloadAndSaveImage(pokemonName, "https://test.com/pikachu.png")).thenReturn(imagePath);
        when(paletteExtractingService.getColorCount(eq(imagePath), anyInt())).thenReturn(colorCount);
        when(paletteExtractingService.extractPalette(imagePath, colorCount)).thenReturn(dummyPalette);
        when(colorMatchingService.matchPaletteToFlosses(dummyPalette)).thenReturn(dummyMatches);

        PokemonMatchResult result = pokemonMatchService.getMatchResult(pokemonName);

        assertNotNull(result);
        assertEquals(pokemonName, result.name());
        assertEquals(imagePath.toString().replace("\\", "/"), result.imagePath());
        assertEquals(dummyMatches, result.matches());

        verify(resultStorageService).saveResult(result, pokemonName);
    }

    @Test
    void givenNoExistingResult_givenExistingSavedImage_whenGetMatchResult_thenWorkflowExecutesCorrectly() throws Exception {

        int colorCount = 2;
        int[][] dummyPalette = new int[][]{{0, 0, 0}, {255, 255, 255}};
        List<FlossColorMatch> dummyMatches = List.of(
                new FlossColorMatch(new int[]{0, 0, 0}, new DmcFloss("310", "Black", "#000000")),
                new FlossColorMatch(new int[]{255, 255, 255}, new DmcFloss("B5200", "White", "#ffffff"))
        );

        when(resultStorageService.readResult(pokemonName)).thenReturn(null);
        when(imageService.getSavedImage(pokemonName)).thenReturn(imagePath);
        when(paletteExtractingService.getColorCount(eq(imagePath), anyInt())).thenReturn(colorCount);
        when(paletteExtractingService.extractPalette(imagePath, colorCount)).thenReturn(dummyPalette);
        when(colorMatchingService.matchPaletteToFlosses(dummyPalette)).thenReturn(dummyMatches);

        PokemonMatchResult result = pokemonMatchService.getMatchResult(pokemonName);

        assertNotNull(result);
        assertEquals(pokemonName, result.name());
        assertEquals(imagePath.toString().replace("\\", "/"), result.imagePath());
        assertEquals(dummyMatches, result.matches());

        verify(resultStorageService).saveResult(result, pokemonName);
    }

    @Test
    void givenExistingResult_whenGetMatchResult_thenCachedResult() throws IOException {

        PokemonMatchResult cachedResult = new PokemonMatchResult(pokemonName,
                imagePath.toString().replace("\\", "/"), List.of());

        when(resultStorageService.readResult(pokemonName)).thenReturn(cachedResult);

        PokemonMatchResult actualResult = pokemonMatchService.getMatchResult(pokemonName);

        assertNotNull(actualResult);
        assertSame(cachedResult, actualResult);
        verifyNoInteractions(pokemonService, imageService, paletteExtractingService, colorMatchingService);
    }


}