package io.github.ctalb.pokecolormatcher.service;

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
}
