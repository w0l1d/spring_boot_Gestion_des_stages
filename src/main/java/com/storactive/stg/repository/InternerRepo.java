package com.storactive.stg.repository;

import com.storactive.stg.model.Interner;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternerRepo extends DataTablesRepository<Interner, Integer> {
    Optional<Interner> findByUsername(String username);

    Optional<Interner> findByIdAndUsernameAndEnabledIsTrue(int id, String username);
}
