package com.greenUs.server.attachment.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.marvinproject.image.transform.scale.Scale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.attachment.exception.FailConvertOutputStream;
import com.greenUs.server.attachment.exception.NotEqualAttachmentAndPostAttachment;
import com.greenUs.server.attachment.exception.NotFoundObjectException;
import com.greenUs.server.attachment.repository.AttachmentRepository;
import com.greenUs.server.post.domain.Post;

import lombok.RequiredArgsConstructor;
import marvin.image.MarvinImage;

@RequiredArgsConstructor
@Service
public class AttachmentService {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	private static final int FREE_INFO_POST_WIDTH = 100;
	private static final int USED_POST_WIDTH = 427;
	private final AmazonS3 amazonS3;
	private final AttachmentRepository attachmentRepository;

	public void createAttachment(Post post, List<MultipartFile> multipartFiles) {

		for (MultipartFile file : multipartFiles) {

			// 원본 이미지
			String serverFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

			// 썸네일 (원본 이미지 축소)
			String thumbnailFileName = "s_" + serverFileName;
			String fileFormatName = file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1);
			MultipartFile thumbnailFile = resizeAttachment(thumbnailFileName, fileFormatName, file,
				getTargetWidth(post.getKind()));

			putAmazonS3Object(serverFileName, file, new ObjectMetadata());
			putAmazonS3Object(thumbnailFileName, thumbnailFile, new ObjectMetadata());

			attachmentRepository.save(
				Attachment.builder()
					.post(post)
					.originalFileName(file.getOriginalFilename())
					.serverFileName(serverFileName)
					.thumbnailFileName(thumbnailFileName)
					.serverFileUrl(getAmazonS3Url(serverFileName))
					.thumbnailFileUrl(getAmazonS3Url(thumbnailFileName))
					.build());
		}
	}

	public void deleteAttachment(Long postId, List<String> serverFileNames) {

		for (String serverFileName : serverFileNames) {

			if (!"".equals(serverFileName) && serverFileName != null) {
				boolean isExistObject = amazonS3.doesObjectExist(bucket, serverFileName);

				if (!isExistObject) {
					throw new NotFoundObjectException();
				} else {
					Attachment attachment = getAttachmentByServerFileName(serverFileName);

					if (!attachment.getPost().getId().equals(postId))
						throw new NotEqualAttachmentAndPostAttachment();
					attachmentRepository.delete(attachment);
					amazonS3.deleteObject(bucket, serverFileName);
				}
			}
		}
	}

	public List<Attachment> getAttachmentInfoByPostId(Long postId) {
		return attachmentRepository.findByPostId(postId);
	}

	private Attachment getAttachmentByServerFileName(String serverFileName) {
		return attachmentRepository.findByServerFileName(serverFileName);
	}

	private MultipartFile resizeAttachment(String fileName, String fileFormatName, MultipartFile multipartFile,
		int targetWidth) {

		try {
			// MultipartFile -> BufferedImage Convert
			BufferedImage image = ImageIO.read(multipartFile.getInputStream());

			// newWidth : newHeight = originWidth : originHeight
			int originWidth = image.getWidth();
			int originHeight = image.getHeight();

			// origin 이미지가 resizing될 사이즈보다 작을 경우 resizing 작업 안 함
			if (originWidth < targetWidth)
				return multipartFile;

			MarvinImage imageMarvin = new MarvinImage(image);

			Scale scale = new Scale();
			scale.load();
			scale.setAttribute("newWidth", targetWidth);
			scale.setAttribute("newHeight", targetWidth * originHeight / originWidth);
			scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

			BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(imageNoAlpha, fileFormatName, baos);
			baos.flush();

			return new MockMultipartFile(fileName, baos.toByteArray());

		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 리사이즈에 실패했습니다.");
		}
	}

	private String getAmazonS3Url(String fileName) {
		return amazonS3.getUrl(bucket, fileName).toString();
	}

	private void putAmazonS3Object(String fileName, MultipartFile file, ObjectMetadata objMeta) {
		try {
			objMeta.setContentLength(file.getInputStream().available());
			amazonS3.putObject(bucket, fileName, file.getInputStream(), objMeta);
		} catch (IOException e) {
			throw new FailConvertOutputStream();
		}
	}

	private int getTargetWidth(Integer kind) {
		if (kind.equals(2))
			return USED_POST_WIDTH;
		return FREE_INFO_POST_WIDTH;
	}
}