package com.greenUs.server.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import com.oracle.bmc.ConfigFileReader.ConfigFile;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.dto.PostResponseDto;
import com.greenUs.server.post.repository.PostRepository;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreateBucketDetails;
import com.oracle.bmc.objectstorage.model.ListObjects;
import com.oracle.bmc.objectstorage.model.ObjectSummary;
import com.oracle.bmc.objectstorage.requests.CreateBucketRequest;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.GetBucketRequest;
import com.oracle.bmc.objectstorage.requests.GetNamespaceRequest;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.requests.ListObjectsRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetBucketResponse;
import com.oracle.bmc.objectstorage.responses.GetNamespaceResponse;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
import com.oracle.bmc.objectstorage.responses.ListObjectsResponse;
import com.oracle.bmc.objectstorage.transfer.*;
import com.oracle.bmc.objectstorage.transfer.UploadManager.UploadRequest;
import com.oracle.bmc.objectstorage.transfer.UploadManager.UploadResponse;

@SpringBootTest
class PostServiceTest {

	@Autowired
	PostService postService;
	@Autowired
	PostRepository postRepository;

	@Test
	@Transactional
	// @Rollback(value = false)
	@DisplayName("게시물 id로 게시물 조회")
	void getPostDetail() {

		// given
		Post post = new Post(3, "title333333", "content3333333");
		Post savePost = postRepository.save(post);

		// when
		PostResponseDto postResponseDto = postService.getPostDetail(savePost.getId());

		// then
		assertThat(postResponseDto.getId().equals(post.getId()));
	}

	@Test
	@DisplayName("Get Bucket Test")
	public void attachmentTest() throws IOException {
		final String compartmentId = "ocid1.tenancy.oc1..aaaaaaaalywaiyxc3qw75fj7ytwthmcnlciw6d7jrzkfwckwnzepwowljg3a";
		final String bucket = "bucket-20230304-1919";
		final String object = "";

		ConfigFile config = ConfigFileReader.parse("~/ocikey/config", "DEFAULT");

		AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(config);


		ObjectStorage client = new ObjectStorageClient(provider);
		client.setRegion(Region.AP_SEOUL_1);

		System.out.println("Getting the namespace.");
		GetNamespaceResponse namespaceResponse = client.getNamespace(GetNamespaceRequest.builder().build());

		String namespaceName = namespaceResponse.getValue();

		System.out.println("Creating Get bucket request");
		List<GetBucketRequest.Fields> fieldsList = new ArrayList<>(2);
		fieldsList.add(GetBucketRequest.Fields.ApproximateCount);
		fieldsList.add(GetBucketRequest.Fields.ApproximateSize);
		GetBucketRequest request =
			GetBucketRequest.builder()
				.namespaceName(namespaceName)
				.bucketName(bucket)
				.fields(fieldsList)
				.build();

		System.out.println("Fetching bucket details");
		GetBucketResponse response = client.getBucket(request);

		System.out.println("Bucket Name : " + response.getBucket().getName());
		System.out.println("Bucket Compartment : " + response.getBucket().getCompartmentId());
		System.out.println(
			"The Approximate total number of objects within this bucket : "
				+ response.getBucket().getApproximateCount());
		System.out.println(
			"The Approximate total size of objects within this bucket : "
				+ response.getBucket().getApproximateSize());

	}

	@Test
	@DisplayName("Upload Object to Bucket")
	void UploadObjectTest() throws Exception {

		ConfigFile config = ConfigFileReader.parse("~/ocikey/config", "DEFAULT");

		AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(config);

		ObjectStorage client = new ObjectStorageClient(provider);
		client.setRegion(Region.AP_SEOUL_1);

		//upload object
		UploadConfiguration uploadConfiguration =
			UploadConfiguration.builder()
				.allowMultipartUploads(true)
				.allowParallelUploads(true)
				.build();

		UploadManager uploadManager = new UploadManager(client, uploadConfiguration);

		String namespaceName = "cnqmrawcp5js";
		String bucketName = "bucket-20230304-1919";
		String objectName = "test_object2.jpg";
		Map<String, String> metadata = null;
		String contentType = "image/jpg";

		String contentEncoding = null;
		String contentLanguage = null;

		File body = new File("/test.jpg");

		PutObjectRequest request =
			PutObjectRequest.builder()
				.bucketName(bucketName)
				.namespaceName(namespaceName)
				.objectName(objectName)
				.contentType(contentType)
				.contentLanguage(contentLanguage)
				.contentEncoding(contentEncoding)
				.opcMeta(metadata)
				.build();


		UploadRequest uploadDetails =
			UploadRequest.builder(body).allowOverwrite(true).build(request);

		UploadResponse response = uploadManager.upload(uploadDetails);
		System.out.println(response);

		client.close();
	}

	@Test
	@DisplayName("Read Object in Bucket")
	void getObjectOneTest() throws Exception {
		ConfigFile config = ConfigFileReader.parse("~/ocikey/config", "DEFAULT");

		AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(config);

		ObjectStorage client = new ObjectStorageClient(provider);
		client.setRegion(Region.AP_SEOUL_1);

		String namespaceName = "cnqmrawcp5js";
		String bucketName = "bucket-20230304-1919";
		String objectName = "객체이름MVC.png";

		GetObjectRequest request =
			GetObjectRequest.builder()
				.namespaceName(namespaceName)
				.bucketName(bucketName)
				.objectName(objectName)
				.build();

		GetObjectResponse response = client.getObject(request);
		System.out.println(response);
		response.getInputStream();

		client.close();
	}

	@Test
	@DisplayName("Read List Object in Bucket")
	void getObjectListTest() throws Exception {
		ConfigFile config = ConfigFileReader.parse("~/ocikey/config", "DEFAULT");

		AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(config);

		ObjectStorage client = new ObjectStorageClient(provider);
		client.setRegion(Region.AP_SEOUL_1);

		String namespaceName = "cnqmrawcp5js";
		String bucketName = "bucket-20230304-1919";

		ListObjectsRequest request =
			ListObjectsRequest.builder()
				.namespaceName(namespaceName)
				.bucketName(bucketName)
				.fields("size, md5, timeCreated, timeModified")
				.prefix("~~~~")
				.build();

		ListObjectsResponse response = client.listObjects(request);

		ListObjects list = response.getListObjects();
		List<ObjectSummary> objectList = list.getObjects();


		for(int i=0; i<objectList.size(); i++) {
			System.out.println("====================");
			System.out.println("@@@@@@@@@@@@@@@@@ getName : " + objectList.get(i).getName());
			System.out.println("@@@@@@@@@@@@@@@@@ getArchivalState : " + objectList.get(i).getArchivalState());
			System.out.println("@@@@@@@@@@@@@@@@@ getSize : " + objectList.get(i).getSize());
			System.out.println("@@@@@@@@@@@@@@@@@ getTimeCreated : " + objectList.get(i).getTimeCreated());
			System.out.println("@@@@@@@@@@@@@@@@@ getTimeModified : " + objectList.get(i).getTimeModified());
		}

		System.out.println(response.getListObjects());
		client.close();
	}

	@Test
	@DisplayName("Delete Object in Bucket")
	void deleteObjectTest() throws Exception {
		ConfigFile config = ConfigFileReader.parse("~/ocikey/config", "DEFAULT");

		AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(config);

		ObjectStorage client = new ObjectStorageClient(provider);
		client.setRegion(Region.AP_SEOUL_1);

		String namespaceName = "cnqmrawcp5js";
		String bucketName = "bucket-20230304-1919";

		DeleteObjectRequest request =
			DeleteObjectRequest.builder()
				.bucketName(bucketName)
				.namespaceName(namespaceName)
				.objectName("객체이름MVC.png")
				.build();


		client.deleteObject(request);
		client.close();
	}
}