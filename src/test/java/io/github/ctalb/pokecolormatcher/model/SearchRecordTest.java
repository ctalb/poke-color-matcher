package io.github.ctalb.pokecolormatcher.model;

import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchRecordTest {

    @Test
    void searchRecordHasEntityAnnotation() {
        assertTrue(SearchRecord.class.isAnnotationPresent(Entity.class));
    }

    @Test
    void searchRecordStoresFieldsCorrectly() {
        SearchRecord record = new SearchRecord("pikachu", 5);

        assertNotNull(record.getId());
        assertEquals("pikachu", record.getPokemonName());
        assertEquals(5, record.getSearchCount());
    }

    @Test
    void searchRecordSettersAndNoArgsConstructorWork() {
        SearchRecord record = new SearchRecord();
        record.setPokemonName("charizard");
        record.setSearchCount(10);

        assertNotNull(record.getId());
        assertEquals("charizard", record.getPokemonName());
        assertEquals(10, record.getSearchCount());
    }
}
