package com.storactive.stg.service;

import com.storactive.stg.model.Piece;
import com.storactive.stg.repository.PieceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PieceService {

    final PieceRepo pieceRepo;
    final StageService internshipSer;

    @Autowired
    public PieceService(PieceRepo pieceRepo, StageService internshipSer) {
        this.pieceRepo = pieceRepo;
        this.internshipSer = internshipSer;
    }

    public List<Piece> getAll() {
        return pieceRepo.findAll();
    }


    public Piece create(Piece piece) {
        piece.setId(null);
        return pieceRepo.save(piece);
    }

    public Piece update(Piece piece) {
        findById(piece.getId());
        return pieceRepo.save(piece);
    }

    @Transactional
    public void delete(Integer id) {
        findById(id);
        pieceRepo.deleteById(id);
    }


    public Piece findById(int id) {
        return pieceRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Piece Not Found"));
    }


}
