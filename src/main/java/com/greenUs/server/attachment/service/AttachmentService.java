package com.greenUs.server.attachment.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.marvinproject.image.transform.scale.Scale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.attachment.exception.FailConvertOutputStream;
import com.greenUs.server.attachment.exception.FailResizeAttachment;
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
	private final AmazonS3 amazonS3;
	private final AttachmentRepository attachmentRepository;
	private static final int FREE_INFO_POST_WIDTH = 100;
	private static final int FREE_INFO_POST_HEIGHT = 100;
	private static final int USED_POST_WiDTH = 427;
	private static final int USED_POST_HEIGHT = 427;

	public void createAttachment(Post post, List<MultipartFile> multipartFiles) {

		for (MultipartFile file : multipartFiles) {

			String serverFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
			String thumbnailFileName = getThumbnailFileName(serverFileName);

			String fileFormatName = getFileFormatName(file);

			MultipartFile thumbnailFile = resizeAttachment(thumbnailFileName, fileFormatName, file,
				getTargetWidth(post.getKind()), getTargetHeight(post.getKind()));

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
				}

				Attachment attachment = getAttachmentByServerFileName(serverFileName);

				if (!attachment.getPost().getId().equals(postId))
					throw new NotEqualAttachmentAndPostAttachment();

				amazonS3.deleteObject(bucket, serverFileName);
				amazonS3.deleteObject(bucket, getThumbnailFileName(serverFileName));

				attachmentRepository.delete(attachment);
			}
		}
	}

	private MultipartFile resizeAttachment(String fileName, String fileFormatName, MultipartFile multipartFile,
		int targetWidth, int targetHeight) {

		try {
			BufferedImage image = ImageIO.read(multipartFile.getInputStream());

			int originWidth = image.getWidth();
			int originHeight = image.getHeight();

			if (originWidth < targetWidth && originHeight < targetHeight)
				return multipartFile;

			MarvinImage imageMarvin = new MarvinImage(image);

			Scale scale = new Scale();
			scale.load();
			scale.setAttribute("newWidth", targetWidth);
			scale.setAttribute("newHeight", targetHeight);
			scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

			BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(imageNoAlpha, fileFormatName, baos);
			baos.flush();

			return new MockMultipartFile(fileName, baos.toByteArray());

		} catch (IOException e) {
			throw new FailResizeAttachment();
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

	private String getThumbnailFileName(String serverFileName) {
		return "s_" + serverFileName;
	}

	private String getFileFormatName(MultipartFile file) {
		return file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1);
	}

	private int getTargetWidth(Integer kind) {
		if (kind.equals(2))
			return USED_POST_WiDTH;
		return FREE_INFO_POST_WIDTH;
	}

	private int getTargetHeight(Integer kind) {
		if (kind.equals(2))
			return USED_POST_HEIGHT;
		return FREE_INFO_POST_HEIGHT;
	}

	public List<Attachment> getAttachmentByPostId(Long postId) {
		return attachmentRepository.findByPostId(postId);
	}

	private Attachment getAttachmentByServerFileName(String serverFileName) {
		return attachmentRepository.findByServerFileName(serverFileName);
	}
}