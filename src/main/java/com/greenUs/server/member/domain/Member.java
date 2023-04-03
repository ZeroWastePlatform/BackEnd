package com.greenUs.server.member.domain;

import com.greenUs.server.basket.domain.Basket;
import com.greenUs.server.common.BaseEntity;

import javax.persistence.*;

import com.greenUs.server.coupon.domain.Coupon;
import com.greenUs.server.product.domain.ProductLike;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

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

	@Column(name = "token", nullable = false)
	private String token;

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
	protected Member(String email, String nickName, SocialType socialType, String token) {
		this.email = email;
		this.nickname = nickName;
		this.socialType = socialType;
		this.token = token;
	}

	public void changeToken(String token) {
		this.token = token;
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