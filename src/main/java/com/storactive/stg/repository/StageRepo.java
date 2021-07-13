package com.storactive.stg.repository;

import com.storactive.stg.model.Internship;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepo extends DataTablesRepository<Internship, Integer> {
    List<Internship> findAllByInterner_Id(int id);
}
