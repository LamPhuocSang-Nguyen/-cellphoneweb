package com.example.cellphoneweb.responses;


import com.example.cellphoneweb.models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse extends BaseResponse{
    private int productId;
    private String productName;
    private String productDescription;
    private double price;
    private int quantityInStock;


    public static ProductResponse fromProduct(Product product){
        ProductResponse productResponse = ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .price(product.getPrice())
                .quantityInStock(product.getQuantityInStock())
                .build();

        return productResponse;
    }
}
