package com.storactive.stg.repository;

import com.storactive.stg.model.StagePiece;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StagePieceRepo extends DataTablesRepository<StagePiece, Integer> {
}
