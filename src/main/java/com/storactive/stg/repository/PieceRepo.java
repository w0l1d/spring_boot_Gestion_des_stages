package com.storactive.stg.repository;

import com.storactive.stg.model.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRepo extends JpaRepository<Piece, Integer> {
}
