package com.greenUs.server.attachment.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.ConfigFileReader.ConfigFile;

import com.greenUs.server.post.domain.Post;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.transfer.UploadConfiguration;
import com.oracle.bmc.objectstorage.transfer.UploadManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttachmentService {

	@Value("${oraclecloud.objectstorage.bucket}")
	private String bucketName;

	@Value("${oraclecloud.objectstorage.namespace}")
	private String namespace;

	public void applyImage(Post post, MultipartFile multipartFile) throws Exception {

		ConfigFile config = ConfigFileReader.parse("~/ocikey/config", "DEFAULT");

		AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(config);

		ObjectStorage client = new ObjectStorageClient(provider);
		client.setRegion(Region.AP_SEOUL_1);

		UploadConfiguration uploadConfiguration =
			UploadConfiguration.builder()
				.allowMultipartUploads(true)
				.allowParallelUploads(true)
				.build();

		UploadManager uploadManager = new UploadManager(client, uploadConfiguration);

		System.out.println("multipartFile = " + multipartFile.getContentType());
		System.out.println("multipartFile = " + multipartFile.getInputStream());
		System.out.println("multipartFile = " + multipartFile.getResource());


		String objectName = multipartFile.getOriginalFilename();
		Map<String, String> metadata = null;
		String contentType = multipartFile.getContentType();

		String contentEncoding = null;
		String contentLanguage = null;

		File body = multipartToFile(multipartFile);

		PutObjectRequest request =
			PutObjectRequest.builder()
				.bucketName(bucketName)
				.namespaceName(namespace)
				.objectName(objectName)
				.contentType(contentType)
				.contentLanguage(contentLanguage)
				.contentEncoding(contentEncoding)
				.opcMeta(metadata)
				.build();


		UploadManager.UploadRequest uploadDetails =
			UploadManager.UploadRequest.builder(body).allowOverwrite(true).build(request);

		UploadManager.UploadResponse response = uploadManager.upload(uploadDetails);
		System.out.println(response);

		client.close();
	}

	public File multipartToFile(MultipartFile mfile) throws IllegalStateException, IOException {
		File file = new File(mfile.getOriginalFilename());
		mfile.transferTo(file);
		return file;
	}
}
