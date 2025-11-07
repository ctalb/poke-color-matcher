package io.github.ctalb.pokecolormatcher.controller;

import io.github.ctalb.pokecolormatcher.model.PokemonMatchResult;
import io.github.ctalb.pokecolormatcher.service.PokemonMatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PokemonController.class)
class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PokemonMatchService pokemonMatchService;

    @Test
    void givenPokemonName_whenGetMatch_thenReturnsPokemonMatchResult() throws Exception {

        String pokemonName = "pikachu";
        String imagePath = pokemonName + "_default_sprite.png";
        PokemonMatchResult mockResult = new PokemonMatchResult(pokemonName, imagePath, List.of());

        when(pokemonMatchService.getMatchResult(pokemonName)).thenReturn(mockResult);

        mockMvc.perform(get("/api/pokemon/{name}/match", pokemonName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(pokemonName))
                .andExpect(jsonPath("$.imagePath").value(imagePath));

        verify(pokemonMatchService).getMatchResult(pokemonName);
    }

}