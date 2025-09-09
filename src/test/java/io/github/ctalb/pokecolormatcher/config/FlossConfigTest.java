package io.github.ctalb.pokecolormatcher.config;

import io.github.ctalb.pokecolormatcher.model.DmcFloss;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlossConfigTest {

    @Test
    void flossListLoadsFromJson() throws IOException {

        FlossConfig  flossConfig = new FlossConfig();
        List<DmcFloss> flossList = flossConfig.flossList();

        assertNotNull(flossList);
        assertFalse(flossList.isEmpty());
        assertTrue(flossList.stream().anyMatch(f -> f.number().equals("300")));
    }
}