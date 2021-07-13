package com.storactive.stg.repository;

import com.storactive.stg.model.Employee;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends DataTablesRepository<Employee, Integer> {

    Optional<Employee> findByUsername(String username);


}
