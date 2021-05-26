package com.storactive.stg.repository;

import com.storactive.stg.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceRepo extends JpaRepository<Absence, Integer> {
}
