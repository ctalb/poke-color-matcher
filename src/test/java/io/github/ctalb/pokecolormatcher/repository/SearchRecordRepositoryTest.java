package io.github.ctalb.pokecolormatcher.repository;

import io.github.ctalb.pokecolormatcher.model.SearchRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class SearchRecordRepositoryTest {

    @Autowired
    private SearchRecordRepository searchRecordRepository;

    @Test
    void saveAndFindById() {
        SearchRecord record = new SearchRecord("pikachu", 3);
        SearchRecord saved = searchRecordRepository.save(record);

        assertNotNull(saved.getId());
        assertEquals("pikachu", saved.getPokemonName());
        assertEquals(3, saved.getSearchCount());

        Optional<SearchRecord> found = searchRecordRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("pikachu", found.get().getPokemonName());
        assertEquals(3, found.get().getSearchCount());
    }

    @Test
    void findTop3ByOrderBySearchCountDesc_returnsTop3RecordsInDescendingOrder() {
        searchRecordRepository.save(new SearchRecord("bulbasaur", 5));
        searchRecordRepository.save(new SearchRecord("squirtle", 30));
        searchRecordRepository.save(new SearchRecord("pikachu", 10));
        searchRecordRepository.save(new SearchRecord("charizard", 25));
        searchRecordRepository.save(new SearchRecord("eevee", 15));

        List<SearchRecord> top3 = searchRecordRepository.findTop3ByOrderBySearchCountDesc();

        assertEquals(3, top3.size());
        assertEquals("squirtle", top3.get(0).getPokemonName());
        assertEquals(30, top3.get(0).getSearchCount());

        assertEquals("charizard", top3.get(1).getPokemonName());
        assertEquals(25, top3.get(1).getSearchCount());

        assertEquals("eevee", top3.get(2).getPokemonName());
        assertEquals(15, top3.get(2).getSearchCount());
    }
}
