package com.greenUs.server.attachment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.attachment.repository.AttachmentRepository;
import com.greenUs.server.post.domain.Post;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttachmentService {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	private final AmazonS3 amazonS3;
	private final AttachmentRepository attachmentRepository;

	public void createAttachment(Post post, List<MultipartFile> multipartFiles) throws Exception {

		for (MultipartFile multipartFile : multipartFiles) {
			String storedFileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

			ObjectMetadata objMeta = new ObjectMetadata();
			objMeta.setContentLength(multipartFile.getInputStream().available());

			amazonS3.putObject(bucket, storedFileName, multipartFile.getInputStream(), objMeta);

			attachmentRepository.save(
				Attachment.builder()
					.post(post)
					.originalFileName(multipartFile.getOriginalFilename())
					.storedFileName(storedFileName)
					.attachmentUrl(amazonS3.getUrl(bucket, storedFileName).toString())
					.build());
		}
	}

	public void updateAttachment(Long postId, List<String> storedFileNames) {

	}

	public List<String> getAttachmentUrlList(Long postId) {

		List<Attachment> attachments = getAttachmentInfo(postId);

		List<String> attachmentUrls = new ArrayList<>();

		for (Attachment attachment : attachments) {
			attachmentUrls.add(attachment.getAttachmentUrl());
		}

		return attachmentUrls;
	}

	public List<String> getAttachmentStoredFileNameList(Long postId) {

		List<Attachment> attachments = getAttachmentInfo(postId);

		List<String> storedFileNames = new ArrayList<>();

		for (Attachment attachment : attachments) {
			storedFileNames.add(attachment.getStoredFileName());
		}

		return storedFileNames;
	}

	private List<Attachment> getAttachmentInfo(Long postId) {

		return attachmentRepository.findByPostId(postId);
	}
}