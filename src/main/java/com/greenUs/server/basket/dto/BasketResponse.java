package com.greenUs.server.basket.dto;

import java.util.List;
import java.util.Map;

import com.greenUs.server.product.domain.Brand;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BasketResponse {

	private Long basketId;

	private Long productId;

	private String thumbnail;

	private Brand brand;

	private String title;

	private Long optionId;

	private List<Map<String, Integer>> require;

	private List<Map<String, Integer>> bonus;

	private Integer price;

	private Integer deliveryFee;

	private Integer discountRate;

	private Integer stock;
}
