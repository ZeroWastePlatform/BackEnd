package com.greenUs.server.attachment.repository;

import com.greenUs.server.attachment.domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
