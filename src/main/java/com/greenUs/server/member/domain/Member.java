package com.greenUs.server.member.domain;

import com.greenUs.server.common.BaseEntity;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "social_type", nullable = false)
	private SocialType socialType;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "name")
	private String name;

	@Column(name = "nickname")
	private String nickname;

	@Embedded
	private Address address;

	@Column(name = "phone_num")
	private String phoneNum;

	@Column(name = "interest_area")
	private String interestArea;

	@Column(name = "level")
	@ColumnDefault("0")
	private int level;

	@Column(name = "point")
	@ColumnDefault("0")
	private int point;

	@Builder
	protected Member(String email, String name, SocialType socialType) {
		this.email = email;
		this.name = name;
		this.socialType = socialType;
	}

	public void changeInfo(String nickname, Address address, String phoneNum, String interestArea) {
		this.nickname = nickname;
		this.address = Address.builder()
				.zipCode(address.getZipCode())
				.address(address.getAddress())
				.addressDetail(address.getAddressDetail())
				.build();
		this.phoneNum = phoneNum;
		this.interestArea = interestArea;
	}

	public void changeNickName(String nickname) {
		this.nickname = nickname;
	}
}