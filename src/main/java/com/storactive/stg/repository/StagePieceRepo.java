package com.storactive.stg.repository;

import com.storactive.stg.model.StagePiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StagePieceRepo extends JpaRepository<StagePiece, Integer> {
}
