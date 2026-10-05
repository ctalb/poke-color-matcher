package io.github.ctalb.pokecolormatcher.controller;

import io.github.ctalb.pokecolormatcher.model.PokemonMatchResult;
import io.github.ctalb.pokecolormatcher.model.PopularPokemonDto;
import io.github.ctalb.pokecolormatcher.service.PokemonMatchService;
import io.github.ctalb.pokecolormatcher.service.SearchRecordService;
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
    @MockitoBean
    private SearchRecordService searchRecordService;

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

    @Test
    void givenIoException_whenGetMatch_thenReturnsNotFound() throws Exception {

        String pokemonName = "invalidName";

        when(pokemonMatchService.getMatchResult(pokemonName)).thenThrow(new IOException());

        mockMvc.perform(get("/api/pokemon/{name}/match", pokemonName)).andExpect(status().isNotFound());
    }

    @Test
    void whenGetPopularPokemon_thenReturnsTop3PopularPokemon() throws Exception {
        List<PopularPokemonDto> popularPokemon = List.of(
                new PopularPokemonDto("pikachu", 30),
                new PopularPokemonDto("charizard", 25),
                new PopularPokemonDto("eevee", 15)
        );

        when(searchRecordService.getTop3SearchRecords()).thenReturn(popularPokemon);

        mockMvc.perform(get("/api/pokemon/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pokemonName").value("pikachu"))
                .andExpect(jsonPath("$[0].searchCount").value(30))
                .andExpect(jsonPath("$[1].pokemonName").value("charizard"))
                .andExpect(jsonPath("$[1].searchCount").value(25))
                .andExpect(jsonPath("$[2].pokemonName").value("eevee"))
                .andExpect(jsonPath("$[2].searchCount").value(15));

        verify(searchRecordService).getTop3SearchRecords();
    }

}