package com.greenUs.server.post.dto.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.post.domain.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

	@Schema(description = "게시글 작성자", nullable = true)
	private Member member;

	@Schema(description = "게시판 구분(1: 자유게시판, 2: 정보 공유, 3: 중고 거래", nullable = false, allowableValues = {"1", "2", "3"})
	private Integer kind;

	@Schema(description = "게시판 제목", nullable = false)
	private String title;

	@Schema(description = "게시판 내용", nullable = false)
	private String content;

	@Schema(description = "게시판 가격(중고 거래 게시판일 경우)", example = "20000", nullable = true)
	private Integer price;

	@Schema(description = "게시판 파일 첨부 여부(false: 미첨부, true:첨부)", nullable = true)
	private Boolean fileAttached;

	@Schema(description = "삭제할 첨부파일 이름", example = "[삭제할 첨부파일 이름, 삭제할 첨부파일 이름]", nullable = true)
	private List<String> serverFileNames = new ArrayList<>();

	@Schema(description = "게시판 해시태그", example = "#그리너스#지구", nullable = true)
	private String hashtag = "";

	@Schema(description = "게시판 첨부파일", example = "[첨부파일, 첨부파일]", nullable = true)
	private List<MultipartFile> multipartFiles = new ArrayList<>();

	public Post toEntity() {
		return Post.builder()
			.member(member)
			.kind(kind)
			.title(title)
			.content(content)
			.price(price)
			.fileAttached(fileAttached)
			.build();
	}
}


