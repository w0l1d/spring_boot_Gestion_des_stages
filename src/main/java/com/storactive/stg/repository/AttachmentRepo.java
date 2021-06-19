package com.storactive.stg.repository;

import com.storactive.stg.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepo extends JpaRepository<Attachment, Integer> {
}
