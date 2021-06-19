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

    final String OBJ = "Piece";

    final PieceRepo pieceRepo;
    final StageService internshipSer;
    final HistoryService historySer;

    @Autowired
    public PieceService(PieceRepo pieceRepo, StageService internshipSer, HistoryService historySer) {
        this.pieceRepo = pieceRepo;
        this.internshipSer = internshipSer;
        this.historySer = historySer;
    }

    public List<Piece> getAll() {
        return pieceRepo.findAll();
    }


    public Piece create(Piece piece) {
        piece.setId(null);
        Piece piece1 = pieceRepo.save(piece);
        historySer.objetCreated(OBJ, piece1.getId());
        return piece1;
    }

    public Piece update(Piece piece) {
        findById(piece.getId());
        Piece piece1 = pieceRepo.save(piece);
        historySer.objetUpdated(OBJ, piece1.getId());
        return piece1;
    }

    @Transactional
    public void delete(Integer id) {
        findById(id);
        pieceRepo.deleteById(id);
        historySer.objetDeleted(OBJ, id);
    }


    public Piece findById(int id) {
        return pieceRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Piece Not Found"));
    }


}
