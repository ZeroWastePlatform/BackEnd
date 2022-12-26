package com.greenUs.server.post.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.member.domain.Member;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // 작성자 계정 탈퇴시 게시글도 삭제
    private Member member;

    private Integer kind;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Integer price;

    private Integer view_cnt;

    private Integer reply_cnt;

    private Integer recommend_cnt;

    @Builder
    public Post(Integer kind, String title, String content) {
        this.kind = kind;
        this.title = title;
        this.content = content;
    }
}
