package com.greenUs.server.member.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.global.Time;
import com.greenUs.server.post.domain.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPagePostResponse {

	@Schema(description = "게시판 번호", nullable = false, example = "47")
	private Long id;

	@Schema(description = "게시판 제목", nullable = false)
	private String title;

	@Schema(description = "게시판 조회수", example = "24", nullable = false)
	private Integer viewCnt;

	@Schema(description = "게시판 댓글수", example = "14", nullable = false)
	private Integer replyCnt;

	@Schema(description = "게시판 생성일", example = "1분전 / 1일전 / 2023.01.01", nullable = false)
	private String createdAt;

	@Schema(description = "썸네일 경로", nullable = false)
	private List<String> thumbnailUrls = new ArrayList<>();

	public MyPagePostResponse(Post entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.viewCnt = entity.getViewCnt();
		this.replyCnt = entity.getComments().size();
		this.createdAt = Time.calculateTime(entity.getCreatedAt());

		if (entity.getFileAttached() == true) {
			for (Attachment attachment : entity.getAttachments()) {
				this.thumbnailUrls.add(attachment.getThumbnailFileUrl());
			}
		}
	}
}
