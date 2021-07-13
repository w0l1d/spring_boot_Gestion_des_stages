package com.storactive.stg.repository;

import com.storactive.stg.model.Piece;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRepo extends DataTablesRepository<Piece, Integer> {
}
