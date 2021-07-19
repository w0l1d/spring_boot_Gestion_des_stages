package com.storactive.stg.repository;

import com.storactive.stg.model.Interner;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternerRepo extends DataTablesRepository<Interner, Integer> {
    Optional<Interner> findByUsername(String username);

    Optional<Interner> findByIdAndUsernameAndEnabledIsTrue(int id, String username);

    @Query("select count(i) from Interner i, Internship i2 " +
            "where i.id = i2.interner.id " +
            "and current_date between i2.startsAt and i2.endsAt")
    long countInActiveInternship();
}
