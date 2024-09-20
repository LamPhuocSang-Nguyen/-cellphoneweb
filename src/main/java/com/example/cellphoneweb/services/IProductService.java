package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.ProductDTO;
import com.example.cellphoneweb.dtos.ProductImageDTO;
import com.example.cellphoneweb.models.ImageProductEntity;
import com.example.cellphoneweb.models.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    ProductEntity getProductById(long id);
    List<ProductEntity> getAllProducts();
    ProductEntity saveProduct(ProductDTO productDTO);
    ProductEntity updateProduct(long id, ProductDTO productDTO);
    void deleteProduct(long id);
    Page<ProductEntity> getProducts(Pageable pageable);

    List<ImageProductEntity> getAllProductImages(long product_id);
    ImageProductEntity saveProductImage(long product_id, ProductImageDTO productImageDTO);
    void deleteProductImage(long imageId);
}
