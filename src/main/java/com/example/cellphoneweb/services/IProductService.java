package com.example.cellphoneweb.services;
import com.example.cellphoneweb.dtos.ProductDTO;
import com.example.cellphoneweb.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    Product getProductById(int id);
    List<Product> getAllProducts();
    Product saveProduct(ProductDTO productDTO);
    Product updateProduct(int id, ProductDTO productDTO);
    void deleteProduct(int id);
    Page<Product> getProducts(Pageable pageable);

}
