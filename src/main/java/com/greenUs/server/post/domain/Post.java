package com.greenUs.server.post.domain;

import java.util.ArrayList;
import java.util.List;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.hashtag.domain.Hashtag;
import com.greenUs.server.member.domain.Member;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@DynamicUpdate
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    // @OnDelete(action = OnDeleteAction.CASCADE) // 작성자 계정 탈퇴시 게시글도 삭제
    private Member member;

    @Column(nullable = false)
    private Integer kind;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Integer price;

    private Integer viewCnt;

    private Integer replyCnt;

    private Integer recommendCnt;

    @OneToMany(mappedBy = "post")
    List<Hashtag> hashtags = new ArrayList<>();

    @Builder
    public Post(Integer kind, String title, String content, Integer price) {
        this.kind = kind;
        this.title = title;
        this.content = content;
        this.price = price;
    }

    // 글 수정
    public void update (Integer kind, String title, String content, Integer price) {
        this.kind = kind;
        this.title = title;
        this.content = content;
        this.price = price;
    }

    // insert시 null값 0으로 초기화 (reply_cnt는 조인을 통해 추후 구현)
    @PrePersist
    public void prePersist() {
        this.viewCnt = this.viewCnt == null ? 0 : this.viewCnt;
        this.recommendCnt = this.recommendCnt == null ? 0 : this.recommendCnt;
    }
}
