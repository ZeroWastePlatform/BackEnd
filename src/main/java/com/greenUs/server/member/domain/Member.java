package com.greenUs.server.member.domain;

import com.greenUs.server.basket.domain.Basket;
import com.greenUs.server.common.BaseEntity;

import javax.persistence.*;

import com.greenUs.server.product.domain.ProductLike;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
	private List<Basket> baskets = new ArrayList<>();

	@OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
	private List<ProductLike> ProductLikes = new ArrayList<>();

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

	public void changeInfo(String nickname, Address address, String phoneNum, String interestArea) {
		this.nickname = nickname;
		this.address = new Address(address.getZipCode(), address.getAddress(), address.getAddressDetail());
		this.phoneNum = phoneNum;
		this.interestArea = interestArea;
	}
}