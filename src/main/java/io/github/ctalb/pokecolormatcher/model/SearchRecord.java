package io.github.ctalb.pokecolormatcher.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a search record entity containing a Pokémon name and search count.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class SearchRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pokemonName;
    private int searchCount;

    public SearchRecord(String pokemonName, int searchCount) {
        this.pokemonName = pokemonName;
        this.searchCount = searchCount;
    }
}
