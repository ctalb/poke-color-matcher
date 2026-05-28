package io.github.ctalb.pokecolormatcher.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctalb.pokecolormatcher.model.DmcFloss;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Configuration class for loading DMC embroidery floss data.
 */
@Configuration
public class FlossConfig {

    /**
     * Loads the list of DMC flosses from the dmc-floss.json resource file.
     * @return a list of all available DmcFloss objects.
     * @throws IOException if the resource file cannot be read.
     */
    @Bean
    public List<DmcFloss> flossList() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<DmcFloss>> flossTypeReference = new TypeReference<>() {
        };
        InputStream inputStream = DmcFloss.class.getClassLoader().getResourceAsStream("dmc-floss.json");
        return mapper.readValue(inputStream, flossTypeReference);
    }
}
