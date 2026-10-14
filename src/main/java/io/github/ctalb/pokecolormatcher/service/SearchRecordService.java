package io.github.ctalb.pokecolormatcher.service;

import io.github.ctalb.pokecolormatcher.model.PopularPokemonDto;
import io.github.ctalb.pokecolormatcher.model.SearchRecord;
import io.github.ctalb.pokecolormatcher.repository.SearchRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchRecordService {
    private final SearchRecordRepository searchRecordRepository;

    public SearchRecordService(SearchRecordRepository searchRecordRepository) {
        this.searchRecordRepository = searchRecordRepository;
    }

    public void updateSearchRecord(String pokemonName) {
        Optional<SearchRecord> record = searchRecordRepository.findByPokemonName(pokemonName);

        if (record.isPresent()) {
            int newCount = record.get().getSearchCount() + 1;
            record.get().setSearchCount(newCount);
            searchRecordRepository.save(record.get());
        } else {
            SearchRecord newRecord = new SearchRecord();
            newRecord.setPokemonName(pokemonName);
            newRecord.setSearchCount(1);
            searchRecordRepository.save(newRecord);
        }
    }

    public List<PopularPokemonDto> getTop3SearchRecords() {
        List<SearchRecord> records = searchRecordRepository.findTop3ByOrderBySearchCountDesc();

        return records.stream()
                .map(record -> new PopularPokemonDto(record.getPokemonName(), record.getSearchCount()))
                .toList();
    }

}
