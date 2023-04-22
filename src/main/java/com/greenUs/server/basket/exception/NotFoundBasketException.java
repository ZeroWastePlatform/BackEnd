package com.greenUs.server.basket.exception;

public class NotFoundBasketException extends RuntimeException {

	public NotFoundBasketException(String message) {
		super(message);
	}

	public NotFoundBasketException() {
		this("장바구니가 존재하지 않습니다");
	}
}
