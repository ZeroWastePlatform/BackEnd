package com.greenUs.server.hashtag.domain;

import javax.persistence.*;

@Entity
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;
}
