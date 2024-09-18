package com.example.cellphoneweb.responses;

import com.example.cellphoneweb.models.Products;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private int product_id;

    public static ProductResponse fromProduct(Products product) {
        ProductResponse productResponse = ProductResponse.builder()
                .product_id(product.getProductId())
                .build();
        return productResponse;
    }
}
