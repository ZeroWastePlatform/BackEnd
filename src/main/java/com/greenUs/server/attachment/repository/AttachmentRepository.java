package com.greenUs.server.attachment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenUs.server.attachment.domain.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

	List<Attachment> findByPostId(Long postId);

	Attachment findByStoredFileName(String StoredFileName);
}
