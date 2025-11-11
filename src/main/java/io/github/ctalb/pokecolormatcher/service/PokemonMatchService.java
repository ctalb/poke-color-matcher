package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.FlossColorMatch;
import io.github.ctalb.pokecolormatcher.model.PokemonMatchResult;
import io.github.ctalb.pokecolormatcher.service.image.ImageService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;

@Service
public class PokemonMatchService {

    private final PokemonService pokemonService;
    private final ImageService imageService;
    private final PaletteExtractingService paletteExtractingService;
    private final ColorMatchingService colorMatchingService;
    private final ResultStorageService resultStorageService;

    public PokemonMatchService(
            PokemonService pokemonService,
            ImageService imageService,
            PaletteExtractingService paletteExtractingService,
            ColorMatchingService colorMatchingService,
            ResultStorageService resultStorageService
    ) {
        this.pokemonService = pokemonService;
        this.imageService = imageService;
        this.paletteExtractingService = paletteExtractingService;
        this.colorMatchingService = colorMatchingService;
        this.resultStorageService = resultStorageService;
    }

    private static final int MAX_COLORS = 12;

    public PokemonMatchResult getMatchResult(String pokemonName) throws IOException {

        PokemonMatchResult existingResult = tryReadResult(pokemonName);

        if (existingResult != null) {
            return existingResult;
        }

        Path imagePath = getImage(pokemonName, () -> pokemonService.getSpriteDefaultUrl(pokemonName));
        int [][] palette = extractPalette(imagePath);
        List<FlossColorMatch> matchList = matchPalette(palette);

        PokemonMatchResult result = buildMatchResult(pokemonName, imagePath.toString().replace("\\","/"), matchList);
        saveMatchResult(result, pokemonName);

        return result;
    }

    private PokemonMatchResult tryReadResult(String pokemonName) throws IOException {
        return resultStorageService.readResult(pokemonName);
    }

    private Path getImage(String pokemonName, Supplier<String> urlSupplier) {
        Path existingImage = imageService.getSavedImage(pokemonName);
        if (existingImage != null) {
            return existingImage;
        }

        String url = urlSupplier.get();
        return imageService.downloadAndSaveImage(pokemonName, url);
    }

    private int[][] extractPalette(Path imagePath) throws IOException {
        int uniqueColorCount = paletteExtractingService.getColorCount(imagePath, MAX_COLORS);
        return paletteExtractingService.extractPalette(imagePath, uniqueColorCount);
    }

    private List<FlossColorMatch> matchPalette(int[][] palette) {
        return colorMatchingService.matchPaletteToFlosses(palette);
    }

    private PokemonMatchResult buildMatchResult(String pokemonName, String imagePath, List<FlossColorMatch> matchList) {
        return new PokemonMatchResult(pokemonName, imagePath, matchList);
    }

    private void saveMatchResult(PokemonMatchResult result, String pokemonName) throws IOException {
        resultStorageService.saveResult(result, pokemonName);
    }

}
