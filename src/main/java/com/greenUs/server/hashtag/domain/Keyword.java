package com.greenUs.server.hashtag.domain;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Keyword {

    @Id @GeneratedValue
    @Column(name = "keyword_id")
    private Long id;

    @Column(length = 50)
    private String content;

    private Integer count;

    @Builder
    public Keyword(String content, Integer count) {
        this.content = content;
        this.count = count;
    }

    public void plusCount() {
        this.count += 1;
    }
}
