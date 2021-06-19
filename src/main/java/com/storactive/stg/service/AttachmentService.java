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

    AttachmentRepo attachmentRepo;


    @Autowired
    public AttachmentService(AttachmentRepo attachmentRepo) {
        this.attachmentRepo = attachmentRepo;
    }

    public List<Attachment> getAll() {
        return attachmentRepo.findAll();
    }


    public Attachment create(Attachment attachment) {
        attachment.setId(null);
        return attachmentRepo.save(attachment);
    }

    @Transactional
    public Attachment update(Attachment attachment) {
        findById(attachment.getId());
        return attachmentRepo.save(attachment);
    }

    @Transactional
    public void delete(Integer id) {
        attachmentRepo.deleteById(id);
    }


    public Attachment findById(int id) {
        return attachmentRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attachment Not Found"));
    }


}
