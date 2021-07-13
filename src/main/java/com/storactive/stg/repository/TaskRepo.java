package com.storactive.stg.repository;

import com.storactive.stg.model.Task;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends DataTablesRepository<Task, Integer> {

}
