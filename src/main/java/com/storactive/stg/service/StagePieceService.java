package com.storactive.stg.service;

import com.storactive.stg.model.Attachment;
import com.storactive.stg.model.Internship;
import com.storactive.stg.model.Piece;
import com.storactive.stg.model.StagePiece;
import com.storactive.stg.repository.StagePieceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class StagePieceService {
    final String OBJ = "Stage Piece";

    final StagePieceRepo stagePieceRepo;

    final HistoryService historySer;
    final StageService internshipSer;
    final AttachmentService attachmentSer;
    final FileStorageService storageSer;
    final PieceService pieceSer;


    @Autowired
    public StagePieceService(StagePieceRepo stagePieceRepo,
                             HistoryService historySer, PieceService pieceSer,
                             AttachmentService attachmentSer,
                             FileStorageService storageSer,
                             StageService internshipSer) {
        this.stagePieceRepo = stagePieceRepo;
        this.historySer = historySer;
        this.internshipSer = internshipSer;
        this.attachmentSer = attachmentSer;
        this.storageSer = storageSer;
        this.pieceSer = pieceSer;
    }

    public List<StagePiece> getAll() {
        return stagePieceRepo.findAll();
    }

    @Transactional
    public StagePiece create(Piece piece,
                             Internship internship,
                             MultipartFile file) {

        //save new file in file system
        Attachment newAttachment = storageSer.save(file);

        //create object related to file stored
        newAttachment = attachmentSer.create(newAttachment);

        // create new StagePiece
        StagePiece stagePiece = new StagePiece();

        //related objects from stagePiece side
        stagePiece.setAttachment(newAttachment);
        stagePiece.setPiece(piece);
        stagePiece.setInternship(internship);
        stagePiece.setDateSaisie(new Date());

        //create StagePiece
        stagePiece = stagePieceRepo.save(stagePiece);

        //related stagePiece from other objects side
        //relate internship to StagePiece
        internship.getStagePieces().add(stagePiece);
        //relate piece to StagePiece
        piece.getStagePieces().add(stagePiece);

        historySer.objetCreated(OBJ, stagePiece.getId());
        return stagePiece;
    }


    @Transactional
    public StagePiece update(Integer stagePieceId, MultipartFile file) {
        StagePiece stagePiece = findById(stagePieceId);

        //get old attachment object
        Attachment oldAttachment = stagePiece.getAttachment();

        //delete old file stored
        storageSer.delete(oldAttachment.getPath());

        //save new file in file system
        Attachment newAttachment = storageSer.save(file);

        //create object related to file stored
        newAttachment = attachmentSer.create(newAttachment);

        //delete attachement object related to old file
        attachmentSer.delete(stagePiece.getAttachment().getId());

        //relate new attachment created to StagePiece
        stagePiece.setAttachment(newAttachment);

        //finally create stagePiece
        StagePiece updatedStagePiece = stagePieceRepo.save(stagePiece);

        historySer.objetUpdated(OBJ, updatedStagePiece.getId());

        return updatedStagePiece;
    }

    @Transactional
    public void delete(Integer id) {
        StagePiece stagePiece = findById(id);

        //get StagePiece attachment
        Attachment attachment = stagePiece.getAttachment();

        //Unlink stagePiece from internship
        Internship internship = stagePiece.getInternship();
        internship.getStagePieces().remove(stagePiece);
        internshipSer.update(internship);

        //Unlink stagePiece from Piece
        Piece piece = stagePiece.getPiece();
        piece.getStagePieces().remove(stagePiece);
        pieceSer.update(piece);

        //delete file from file system
        storageSer.delete(attachment.getPath());

        //delete attachment from db
        attachmentSer.delete(attachment.getId());

        //delete stagePiece
        stagePieceRepo.deleteById(id);

        historySer.objetDeleted(OBJ, id);
    }


    public StagePiece findById(int id) {
        return stagePieceRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "StagePiece Not Found"));
    }


}
