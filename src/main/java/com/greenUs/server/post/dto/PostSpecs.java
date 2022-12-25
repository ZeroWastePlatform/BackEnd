package com.greenUs.server.post.dto;

import org.springframework.data.jpa.domain.Specification;

import com.greenUs.server.post.domain.Post;

public class PostSpecs {
	public static Specification<Post> withKind(Integer kind) {
		return (Specification<Post>) ((root, query, builder) ->
			builder.equal(root.get("kind"), kind)
		);
	}

	public static Specification<Post> withId(Integer id) {
		return (Specification<Post>) ((root, query, builder) ->
			builder.equal(root.get("id"), id)
		);
	}
}