package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.PopularPokemonDto;
import io.github.ctalb.pokecolormatcher.model.SearchRecord;
import io.github.ctalb.pokecolormatcher.repository.SearchRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchRecordServiceTest {

    @Mock
    private SearchRecordRepository searchRecordRepository;

    @InjectMocks
    private SearchRecordService searchRecordService;

    @Test
    void updateSearchRecord_whenRecordExists_incrementsCountByOneAndSaves() {
        SearchRecord existingRecord = new SearchRecord("pikachu", 3);
        when(searchRecordRepository.findByPokemonName("pikachu")).thenReturn(Optional.of(existingRecord));

        searchRecordService.updateSearchRecord("pikachu");

        assertEquals(4, existingRecord.getSearchCount());
        verify(searchRecordRepository).save(existingRecord);
    }

    @Test
    void updateSearchRecord_whenRecordDoesNotExist_createsNewRecordWithCountOneAndSaves() {
        when(searchRecordRepository.findByPokemonName("bulbasaur")).thenReturn(Optional.empty());

        searchRecordService.updateSearchRecord("bulbasaur");

        ArgumentCaptor<SearchRecord> captor = ArgumentCaptor.forClass(SearchRecord.class);
        verify(searchRecordRepository).save(captor.capture());
        SearchRecord savedRecord = captor.getValue();

        assertEquals("bulbasaur", savedRecord.getPokemonName());
        assertEquals(1, savedRecord.getSearchCount());
    }

    @Test
    void getTop3SearchRecords_returnsPopularPokemonDtosFromRepositoryRecords() {
        List<SearchRecord> records = List.of(
                new SearchRecord("squirtle", 30),
                new SearchRecord("charizard", 25),
                new SearchRecord("eevee", 15)
        );
        when(searchRecordRepository.findTop3ByOrderBySearchCountDesc()).thenReturn(records);

        List<PopularPokemonDto> result = searchRecordService.getTop3SearchRecords();

        assertEquals(3, result.size());

        assertEquals("squirtle", result.get(0).pokemonName());
        assertEquals(30, result.get(0).searchCount());

        assertEquals("charizard", result.get(1).pokemonName());
        assertEquals(25, result.get(1).searchCount());

        assertEquals("eevee", result.get(2).pokemonName());
        assertEquals(15, result.get(2).searchCount());

        verify(searchRecordRepository).findTop3ByOrderBySearchCountDesc();
    }
}
