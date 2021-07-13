package com.storactive.stg.repository;

import com.storactive.stg.model.Absence;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceRepo extends DataTablesRepository<Absence, Integer> {
//    DataTablesOutput<Absence> findAllByInternship_Interner(Interner interner, DataTablesInput input);

}
