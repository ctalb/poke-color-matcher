package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.FlossColorMatch;
import io.github.ctalb.pokecolormatcher.model.PokemonMatchResult;
import io.github.ctalb.pokecolormatcher.service.image.ImageDownloader;
import io.github.ctalb.pokecolormatcher.service.image.ImageSaver;
import io.github.ctalb.pokecolormatcher.service.image.ImageService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;

@Service
public class PokemonMatchService {
    /* Orchestrates whole workflow
    - Receive String name
    - Give String name to ResultStorageService readResult(), receive null or PokemonMatchResult result
    - If receive result, return result
    - If receive null, give String name PokemonService getDefaultSpriteUrl(), receive String url
    - Give String url to ImageDownloader downloadImage(), receive byte[] imageData
    - Give byte[] imageData to ImageSaver saveImage(), receive Path imagePath
    - Give Path imagePath to PaletteExtractingService extractPalette(), receive int[][] palette
    - Give int[][] palette to ColorMatchingService, receive List<FlossColorMatch> matches
    - Create PokemonMatchResult result (String name, String imagePath, List<FlossColorMatch> matches)
    - Give PokemonMatchResult result to ResultStorageService saveResult(), receive nothing
    - Return PokemonMatchResult result
    */

    private final PokemonService pokemonService;
    private final ImageDownloader imageDownloader;
    private final ImageSaver imageSaver;
    private final ImageService imageService;
    private final PaletteExtractingService paletteExtractingService;
    private final ColorMatchingService colorMatchingService;
    private final ResultStorageService resultStorageService;

    public PokemonMatchService(
            PokemonService pokemonService,
            ImageDownloader imageDownloader,
            ImageSaver imageSaver,
            ImageService imageService,
            PaletteExtractingService paletteExtractingService,
            ColorMatchingService colorMatchingService,
            ResultStorageService resultStorageService
    ) {
        this.pokemonService = pokemonService;
        this.imageDownloader = imageDownloader;
        this.imageSaver = imageSaver;
        this.imageService = imageService;
        this.paletteExtractingService = paletteExtractingService;
        this.colorMatchingService = colorMatchingService;
        this.resultStorageService = resultStorageService;
    }

    public PokemonMatchResult getMatchResult(String pokemonName) throws IOException {

        PokemonMatchResult existingResult = tryReadResult(pokemonName);

        if (existingResult != null) {
            return existingResult;
        }

        //String spriteUrl = getUrl(pokemonName);
        //Path imagePath = downloadAndSaveImage(spriteUrl, pokemonName);
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

    private String getUrl(String pokemonName) {
        return pokemonService.getSpriteDefaultUrl(pokemonName);
    }

    private Path downloadAndSaveImage(String spriteUrl, String pokemonName) {
        byte[] imageData = imageDownloader.downloadImage(spriteUrl);
        return imageSaver.saveImage(pokemonName,imageData);
    }

    private Path getImage(String pokemonName, Supplier<String> urlSupplier) {
        Path existingImage = imageService.getSavedImage(pokemonName);
        if (existingImage != null) {
            return existingImage;
        }

        String url = urlSupplier.get();
        return imageService.downloadAndSaveImage(url, pokemonName);
    }

    private int[][] extractPalette(Path imagePath) throws IOException {
        int MAX_COLORS = 12;
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
