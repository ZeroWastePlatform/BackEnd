package com.greenUs.server.basket.exception;

public class NotEqualMemberAndBasketMember extends RuntimeException {

	public NotEqualMemberAndBasketMember(String message) {
		super(message);
	}

	public NotEqualMemberAndBasketMember() {
		this("장바구니를 가진 멤버가 일치하지 않습니다.");
	}
}