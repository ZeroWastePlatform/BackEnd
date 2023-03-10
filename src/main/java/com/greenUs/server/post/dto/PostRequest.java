package com.greenUs.server.post.dto;

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

	@Schema(description = "게시판 파일 첨부 여부(0: 미첨부, 1:첨부)", nullable = true)
	private Integer fileAttached;

	@Schema(description = "게시판 해시태그", example = "#그리너스#지구")
	private String hashtag = "";

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


