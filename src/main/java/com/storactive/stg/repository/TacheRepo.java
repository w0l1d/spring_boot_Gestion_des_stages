package com.storactive.stg.repository;

import com.storactive.stg.model.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacheRepo extends JpaRepository<Tache, Integer> {
}
