package com.greenUs.server.attachment.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.greenUs.server.attachment.dto.AttachmentRequest;
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

	public void createAttachment(Post post, MultipartFile multipartFile) throws Exception {

		String storedFileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

		ObjectMetadata objMeta = new ObjectMetadata();
		objMeta.setContentLength(multipartFile.getInputStream().available());

		amazonS3.putObject(bucket, storedFileName, multipartFile.getInputStream(), objMeta);

		attachmentRepository.save(
			new AttachmentRequest(post, multipartFile.getOriginalFilename(), storedFileName).toEntity());
	}

	public String getAttachment(String storedFileName) {

		return amazonS3.getUrl(bucket, storedFileName).toString();
	}
}