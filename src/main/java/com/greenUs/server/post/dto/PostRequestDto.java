package com.greenUs.server.post.dto;

import org.springframework.web.multipart.MultipartFile;

import com.greenUs.server.post.domain.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {

	@Schema(description = "게시판 구분(1: 자유게시판, 2: 정보 공유, 3: 중고 거래", nullable = false, allowableValues = {"1", "2", "3"})
	private Integer kind;

	@Schema(description = "게시판 제목", nullable = false)
	private String title;

	@Schema(description = "게시판 내용", nullable = false)
	private String content;

	@Schema(description = "게시판 가격(중고 거래 게시판일 경우)", example = "20000", nullable = true)
	private Integer price;

	@Schema(description = "게시판 해시태그", example = "#그리너스#지구", nullable = true)
	private String hashtag;

	@Schema(description = "게시판 첨부파일", example = "???", nullable = true)
	private MultipartFile postFile;
	private String originalFileName; // 원본 파일 이름
	private String storedFileName; // 서버 저장용 파일 이름(원본 파일이 같은 이름으로 여러개 올라온다면 서버에서 구별하기 힘드므로 서버 저장용 파일 이름 구분)
	private Integer fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)


	public Post toEntity() {
		return Post.builder()
			.kind(kind)
			.title(title)
			.content(content)
			.price(price)
			.build();
	}

	public Post toFileSaveEntity() {
		return Post.builder()
			.kind(kind)
			.title(title)
			.content(content)
			.price(price)
			.fileAttached(1)
			.build();
	}
}


