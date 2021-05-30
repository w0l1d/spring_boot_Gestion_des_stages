package com.storactive.stg.repository;

import com.storactive.stg.model.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepo extends JpaRepository<Internship, Integer> {
}
