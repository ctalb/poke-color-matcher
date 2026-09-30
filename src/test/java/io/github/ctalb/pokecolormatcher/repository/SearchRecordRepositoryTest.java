package io.github.ctalb.pokecolormatcher.repository;

import io.github.ctalb.pokecolormatcher.model.SearchRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
}
