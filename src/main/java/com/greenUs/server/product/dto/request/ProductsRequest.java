package com.greenUs.server.product.dto.request;

import com.greenUs.server.product.domain.Brand;
import com.greenUs.server.product.domain.Category;
import com.greenUs.server.product.domain.ProductStatus;
import com.greenUs.server.product.dto.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductsRequest {
    private Category category;
    private Integer page = 0;
    private Order order;
    private Brand brand;
    private String price;
    private ProductStatus productStatus;
}
