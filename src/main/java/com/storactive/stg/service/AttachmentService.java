package com.storactive.stg.service;

import com.storactive.stg.model.Attachment;
import com.storactive.stg.repository.AttachmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttachmentService {

    final String OBJ = "Piece Joint";

    final AttachmentRepo attachmentRepo;
    final HistoryService historySer;
    final AlertService alertSer;


    public Attachment create(Attachment attachment) {
        attachment.setId(null);
        Attachment attachment1 = attachmentRepo.save(attachment);
        historySer.objetCreated(OBJ, attachment1.getId());
        return attachment1;
    }

    public Attachment update(Attachment attachment) {
        findById(attachment.getId());
        Attachment attachment1 = attachmentRepo.save(attachment);
        historySer.objetUpdated(OBJ, attachment1.getId());
        return attachment1;
    }

    public void delete(Integer id) {
        attachmentRepo.deleteById(id);
        historySer.objetDeleted(OBJ, id);
    }


    public Attachment findById(int id) {
        return attachmentRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attachment Not Found"));
    }


}
