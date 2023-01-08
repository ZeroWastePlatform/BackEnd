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

    @Builder
    public Keyword(String content) {
        this.content = content;
    }
}
