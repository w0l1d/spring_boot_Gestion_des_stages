package com.storactive.stg.repository;

import com.storactive.stg.model.Alert;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepo extends JpaRepository<Alert, Integer>, DataTablesRepository<Alert, Integer> {
    long countByStatusEquals(short status);
}
