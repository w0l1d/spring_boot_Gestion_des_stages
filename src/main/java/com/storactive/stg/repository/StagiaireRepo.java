package com.storactive.stg.repository;

import com.storactive.stg.model.Stagiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StagiaireRepo extends JpaRepository<Stagiaire, Integer> {

}
