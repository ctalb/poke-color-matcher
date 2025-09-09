package io.github.ctalb.pokecolormatcher.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctalb.pokecolormatcher.model.DmcFloss;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class FlossConfig {

    @Bean
    public List<DmcFloss> flossList() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<DmcFloss>> flossTypeReference = new TypeReference<>() {
        };
        InputStream inputStream = DmcFloss.class.getClassLoader().getResourceAsStream("dmc-floss.json");
        return mapper.readValue(inputStream, flossTypeReference);
    }
}
