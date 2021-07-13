package com.storactive.stg.repository;

import com.storactive.stg.model.History;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepo extends DataTablesRepository<History, Integer> {
    void deleteAllByCreatedBy_Id(int id);
}
