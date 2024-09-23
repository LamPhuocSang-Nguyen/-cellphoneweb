package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.ProductDTO;
import com.example.cellphoneweb.dtos.ProductImageDTO;
import com.example.cellphoneweb.models.ImageProductEntity;
import com.example.cellphoneweb.models.ProductEntity;
import com.example.cellphoneweb.repositorise.ProductImageRepository;
import com.example.cellphoneweb.repositorise.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public ProductEntity getProductById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity saveProduct(ProductDTO productDTO) {
        ProductEntity product = ProductEntity.builder()
                .name(productDTO.getProductName())
                .description(productDTO.getProductDescription())
                .price(productDTO.getPrice())
                .quantityInStock(productDTO.getQuantityInStock())
                .category(productDTO.getCategory())
                .build();
        return productRepository.save(product);
    }

    @Override
    public ProductEntity updateProduct(long id, ProductDTO productDTO) {
        ProductEntity product = getProductById(id);
        product.setName(productDTO.getProductName());
        product.setDescription(productDTO.getProductDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantityInStock(productDTO.getQuantityInStock());
        product.setCategory(productDTO.getCategory());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductEntity> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<ImageProductEntity> getAllProductImages(long product_id) {
        return productImageRepository.findByProduct_Id(product_id);
    }

    @Override
    public ImageProductEntity saveProductImage(long product_id, ProductImageDTO productImageDTO) {
        ProductEntity product = getProductById(product_id);
        ImageProductEntity productImage = ImageProductEntity.builder()
                .product(product)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        int size = productImageRepository.findByProduct_Id(product_id).size();
        if(size > 4){
            throw new IllegalArgumentException("Product image is more than 4");
        }
        return productImageRepository.save(productImage);
    }

    @Override
    public void deleteProductImage(long imageId) {
        productImageRepository.deleteById(imageId);
    }


}
