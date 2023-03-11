package com.greenUs.server.attachment.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.attachment.exception.FailConvertOutputStream;
import com.greenUs.server.attachment.exception.NotEqualAttachmentAndPostAttachment;
import com.greenUs.server.attachment.exception.NotFoundObjectException;
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

	public void createAttachment(Post post, List<MultipartFile> multipartFiles) {

		for (MultipartFile multipartFile : multipartFiles) {
			String storedFileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

			ObjectMetadata objMeta = new ObjectMetadata();
			try {
				objMeta.setContentLength(multipartFile.getInputStream().available());

				amazonS3.putObject(bucket, storedFileName, multipartFile.getInputStream(), objMeta);
			} catch (IOException e) {
				throw new FailConvertOutputStream();
			}

			attachmentRepository.save(
				Attachment.builder()
					.post(post)
					.originalFileName(multipartFile.getOriginalFilename())
					.storedFileName(storedFileName)
					.attachmentUrl(amazonS3.getUrl(bucket, storedFileName).toString())
					.build());
		}
	}

	public void deleteAttachment(Long postId, List<String> storedFileNames) {

		for (String storedFileName : storedFileNames) {

			if ("".equals(storedFileName) == false && storedFileName != null) {
				boolean isExistObject = amazonS3.doesObjectExist(bucket, storedFileName);

				if (!isExistObject) {
					throw new NotFoundObjectException();
				}
				else {
					Attachment attachment = getAttachmentInfoByStoredFileName(storedFileName);

					if (!attachment.getPost().getId().equals(postId))
						throw new NotEqualAttachmentAndPostAttachment();
					attachmentRepository.delete(attachment);
					amazonS3.deleteObject(bucket, storedFileName);
				}
			}
		}
	}

	public List<Attachment> getAttachmentInfoByPostId(Long postId) {
		return attachmentRepository.findByPostId(postId);
	}

	private Attachment getAttachmentInfoByStoredFileName(String storedFileName) {
		return attachmentRepository.findByStoredFileName(storedFileName);
	}
}