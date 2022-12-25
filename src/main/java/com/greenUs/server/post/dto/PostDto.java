package com.greenUs.server.post.dto;

import com.greenUs.server.post.domain.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDto {

	private Long id;
	private Integer kind;
	private String title;
	private String content;

	public Post toEntity() {
		return Post.builder()
			.kind(kind)
			.title(title)
			.content(content)
			.build();
	}

	@Builder
	public PostDto(Long id, Integer kind, String title, String content) {
		this.id = id;
		this.kind = kind;
		this.title = title;
		this.content = content;
	}

}


