package com.storactive.stg.repository;

import com.storactive.stg.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepo extends JpaRepository<History, Integer> {
}
