package com.greenUs.server.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.domain.Recommend;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

	Recommend findByPostAndMember(Post post, Member member);
}
