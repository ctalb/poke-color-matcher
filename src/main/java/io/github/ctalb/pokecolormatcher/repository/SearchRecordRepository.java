package io.github.ctalb.pokecolormatcher.repository;

import io.github.ctalb.pokecolormatcher.model.SearchRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link SearchRecord} entities.
 */
@Repository
public interface SearchRecordRepository extends CrudRepository<SearchRecord, Long> {
}
