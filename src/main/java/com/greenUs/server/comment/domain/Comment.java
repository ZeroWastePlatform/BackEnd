package com.greenUs.server.comment.domain;

import java.util.ArrayList;
import java.util.List;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.post.domain.Post;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Entity
public class Comment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @ToString.Exclude
    private Post post;

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Builder
    public Comment(Post post, String content) {
        this.post = post;
        this.content = content;
    }

    public void update (String content) {
        this.content = content;
    }
}
