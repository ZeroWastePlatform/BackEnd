package com.greenUs.server.hashtag.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.greenUs.server.post.domain.Post;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Entity
public class Hashtag {

    @Id @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @Builder
    public Hashtag(Post post, Keyword keyword) {
        this.post = post;
        this.keyword = keyword;
    }
}
