package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.SearchRecord;
import io.github.ctalb.pokecolormatcher.repository.SearchRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
