package com.greenUs.server.hashtag.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Hashtag {

    @Id @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;
}
