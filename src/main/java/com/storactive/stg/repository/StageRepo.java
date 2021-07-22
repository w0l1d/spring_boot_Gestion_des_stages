package com.storactive.stg.repository;

import com.storactive.stg.model.Internship;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepo extends DataTablesRepository<Internship, Integer> {
    List<Internship> findAllByInterner_Id(int id);

    @Query("select count(i) from Internship i " +
            "where current_date between i.startsAt and i.endsAt and i.interner.id = ?1")
    long countActiveInternship(int internerId);

    @Query("select count(i) from Internship i " +
            "where current_date between i.startsAt and i.endsAt")
    long countActiveInternship();

}
