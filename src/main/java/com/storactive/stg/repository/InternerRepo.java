package com.storactive.stg.repository;

import com.storactive.stg.model.Interner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternerRepo extends JpaRepository<Interner, Integer> {
    Optional<Interner> findByUsername(String username);
}
