package com.storactive.stg.repository;

import com.storactive.stg.model.Interner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternerRepo extends JpaRepository<Interner, Integer> {

}
