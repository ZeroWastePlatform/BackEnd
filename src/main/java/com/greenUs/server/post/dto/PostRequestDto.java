package com.greenUs.server.post.dto;

import com.greenUs.server.post.domain.Post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString // 컨트롤러 인자 테스트용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {

	private Integer kind;
	private String title;
	private String content;
	private Integer price;

	public Post toEntity() {
		return Post.builder()
			.kind(kind)
			.title(title)
			.content(content)
			.price(price)
			.build();
	}
}


