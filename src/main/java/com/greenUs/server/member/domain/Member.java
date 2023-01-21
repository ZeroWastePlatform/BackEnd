package com.greenUs.server.member.domain;

import com.greenUs.server.common.BaseEntity;

import javax.persistence.*;

import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class Member extends BaseEntity {

	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "social_type", nullable = false)
	private SocialType socialType;

	@Column(name = "token", nullable = false)
	private String token;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "nickname")
	private String nickname;

	private String address; // TODO: embedded 로 수정하기

	@Column(name = "phone_num")
	private String phoneNum;

	@Column(name = "interest_area")
	private String interestArea;

	public Member() {}
	public Member(String email, String name, SocialType socialType, String token ) {
		this.email = email;
		this.name = name;
		this.socialType = socialType;
		this.token = token;
	}

	public void change(String token) {
		this.token = token;
	}

	public void changeInfo(String nickname, String address, String phoneNum, String interestArea) {
		this.nickname = nickname;
		this.address = address;
		this.phoneNum = phoneNum;
		this.interestArea = interestArea;
	}
}