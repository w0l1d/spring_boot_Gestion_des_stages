package com.storactive.stg.service;

import com.storactive.stg.model.Attachment;
import com.storactive.stg.model.Internship;
import com.storactive.stg.model.Piece;
import com.storactive.stg.model.StagePiece;
import com.storactive.stg.repository.StagePieceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StagePieceService {
    final String OBJ = "Stage Piece";

    final StagePieceRepo stagePieceRepo;

    final HistoryService historySer;
    final StageService internshipSer;
    final AttachmentService attachmentSer;
    final FileStorageService storageSer;
    final PieceService pieceSer;
    final AlertService alertSer;




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

        //register action in history
        historySer.objetCreated(OBJ, stagePiece.getId());

        //alert Pieces which are not related to internship
        List<Piece> pieces = pieceSer.findAllNotRelatedToInternship(internship);
        alertSer.alertInternshipPieces(internship, pieces);

        return stagePiece;
    }


    public StagePiece update(int stagePieceId, int pieceId, MultipartFile file) {
        StagePiece stagePiece = findById(stagePieceId);

        if (pieceId != stagePiece.getPiece().getId())
            stagePiece.setPiece(pieceSer.findById(pieceId));

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

        //register action in history
        historySer.objetUpdated(OBJ, updatedStagePiece.getId());

        return updatedStagePiece;
    }

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

        //register action in history
        historySer.objetDeleted(OBJ, id);

        //alert Pieces which are not related to internship
        List<Piece> pieces = pieceSer.findAllNotRelatedToInternship(internship);
        alertSer.alertInternshipPieces(internship, pieces);
    }


    public StagePiece findById(int id) {
        return stagePieceRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "StagePiece Not Found"));
    }


}
