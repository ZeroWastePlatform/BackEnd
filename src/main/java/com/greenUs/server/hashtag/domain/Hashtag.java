package com.greenUs.server.hashtag.domain;

import com.greenUs.server.post.domain.Post;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Hashtag {

    @Id @GeneratedValue
    @ToString.Exclude
    @Column(name = "hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Include
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @Builder
    public Hashtag(Post post, Keyword keyword) {
        this.post = post;
        this.keyword = keyword;
    }
}
