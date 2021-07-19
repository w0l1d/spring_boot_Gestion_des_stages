package com.storactive.stg.service;

import com.storactive.stg.model.Piece;
import com.storactive.stg.repository.PieceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Vector;

@Service
@RequiredArgsConstructor
public class PieceService {

    final String OBJ = "Piece";

    final PieceRepo pieceRepo;
    final StageService internshipSer;
    final HistoryService historySer;


    public List<Piece> findAll() {
        List<Piece> result = new Vector<>();
        pieceRepo.findAll().forEach(result::add);
        return result;
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
