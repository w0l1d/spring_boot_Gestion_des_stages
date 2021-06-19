package com.storactive.stg.service;

import com.storactive.stg.model.Attachment;
import com.storactive.stg.repository.AttachmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AttachmentService {

    final String OBJ = "Piece Joint";

    final AttachmentRepo attachmentRepo;
    final HistoryService historySer;


    @Autowired
    public AttachmentService(AttachmentRepo attachmentRepo, HistoryService historySer) {
        this.attachmentRepo = attachmentRepo;
        this.historySer = historySer;
    }

    public List<Attachment> getAll() {
        return attachmentRepo.findAll();
    }


    public Attachment create(Attachment attachment) {
        attachment.setId(null);
        Attachment attachment1 = attachmentRepo.save(attachment);
        historySer.objetCreated(OBJ, attachment1.getId());
        return attachment1;
    }

    @Transactional
    public Attachment update(Attachment attachment) {
        findById(attachment.getId());
        Attachment attachment1 = attachmentRepo.save(attachment);
        historySer.objetUpdated(OBJ, attachment1.getId());
        return attachment1;
    }

    @Transactional
    public void delete(Integer id) {
        attachmentRepo.deleteById(id);
        historySer.create("La piece joint " + id + "est Supprimée");
        historySer.objetDeleted(OBJ, id);
    }


    public Attachment findById(int id) {
        return attachmentRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attachment Not Found"));
    }


}