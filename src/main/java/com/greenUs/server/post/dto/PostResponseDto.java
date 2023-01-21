package com.greenUs.server.post.dto;

import java.util.ArrayList;
import java.util.List;

import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.hashtag.domain.Hashtag;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.post.domain.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PostResponseDto {

	@Schema(description = "게시판 번호", nullable = false, example = "47")
	private Long id;

	@Schema(description = "게시판 구분(1: 자유게시판, 2: 정보 공유, 3: 중고 거래)", nullable = false, allowableValues = {"1", "2", "3"})
	private Integer kind;

	@Schema(description = "게시판 제목", nullable = false)
	private String title;

	@Schema(description = "게시판 작성자", nullable = false)
	private Member member;

	@Schema(description = "게시판 내용", nullable = false)
	private String content;

	@Schema(description = "게시판 가격(중고 거래 게시판일 경우)", example = "20000", nullable = true)
	private Integer price;

	@Schema(description = "게시판 조회수", example = "24", nullable = false)
	private Integer viewCnt;

	@Schema(description = "게시판 댓글수", example = "14", nullable = false)
	private Integer replyCnt;

	@Schema(description = "게시판 추천수", example = "17", nullable = false)
	private Integer recommendCnt;

	@Schema(description = "게시판 해시태그", example = "[그리너스, 지구]", nullable = false)
	private List<String> hashtags = new ArrayList<>();

	@Schema(description = "원본 파일 이름", nullable = true)
	private List<String> originalFileName = new ArrayList<>();

	@Schema(description = "서버 저장용 파일 이름", nullable = true)
	private List<String> storedFileName = new ArrayList<>();

	@Schema(description = "파일 첨부 여부", example = "첨부 : 1, 미첨부 : 0", nullable = false)
	private Integer fileAttached;

	public PostResponseDto(Post entity) {
		this.id = entity.getId();
		this.kind = entity.getKind();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.price = entity.getPrice();
		this.viewCnt = entity.getViewCnt();
		this.replyCnt = entity.getReplyCnt();
		this.recommendCnt = entity.getRecommendCnt();

		for (Hashtag hashtag : entity.getHashtags()) {
			this.hashtags.add(hashtag.getKeyword().getContent());
		}

		if (entity.getFileAttached() == 0) {
			this.fileAttached = entity.getFileAttached();
		} else {
			this.fileAttached = entity.getFileAttached();
			
			for (Attachment attachment : entity.getAttachments()) {
				this.originalFileName.add(attachment.getOriginalFileName());
				this.storedFileName.add(attachment.getStoredFileName());
			}
		}
	}
}
