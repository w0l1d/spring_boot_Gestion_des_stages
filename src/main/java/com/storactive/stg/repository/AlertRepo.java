package com.storactive.stg.repository;

import com.storactive.stg.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepo extends JpaRepository<Alert, Integer>, JpaSpecificationExecutor<Alert> {
    long countByStatusEquals(int status);
}
