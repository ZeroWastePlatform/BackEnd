package com.greenUs.server.product.dto.request;

import com.greenUs.server.product.domain.Brand;
import com.greenUs.server.product.domain.Category;
import com.greenUs.server.product.dto.Order;
import com.greenUs.server.product.dto.Price;
import com.greenUs.server.product.dto.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductsRequest {
    private Category category;
    private Integer page = 0;
    private Order order;
    private Brand brand;
    private Price price;
    private ProductStatus productStatus;
}
