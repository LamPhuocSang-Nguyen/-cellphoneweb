package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.ProductDTO;
import com.example.cellphoneweb.models.Product;
import com.example.cellphoneweb.repositorise.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product saveProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .productName(productDTO.getProductName())
                .productDescription(productDTO.getProductDescription())
                .price(productDTO.getPrice())
                .quantityInStock(productDTO.getQuantityInStock())
                .build();

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(int id, ProductDTO productDTO) {
        Product product = getProductById(id);
        product.setProductName(productDTO.getProductName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantityInStock(product.getQuantityInStock());

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
