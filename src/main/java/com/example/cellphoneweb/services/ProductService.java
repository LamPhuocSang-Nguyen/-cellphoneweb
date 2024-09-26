package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.ProductColorDTO;
import com.example.cellphoneweb.dtos.ProductDTO;
import com.example.cellphoneweb.dtos.ProductImageDTO;
import com.example.cellphoneweb.models.CategoryEntity;
import com.example.cellphoneweb.models.ImageProductEntity;
import com.example.cellphoneweb.models.ProductColorEntity;
import com.example.cellphoneweb.models.ProductEntity;
import com.example.cellphoneweb.repositorise.CategoryRepository;
import com.example.cellphoneweb.repositorise.ProductColorRepository;
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
    private final ProductColorRepository productColorRepository;
    private final CategoryRepository categoryRepository;

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
        CategoryEntity category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("Category with ID " + productDTO.getCategoryId() + " not found"));
        ProductEntity product = ProductEntity.builder()
                .name(productDTO.getProductName())
                .description(productDTO.getProductDescription())
                .price(productDTO.getPrice())
                .quantityInStock(productDTO.getQuantityInStock())
                .category(category)
                .build();
        return productRepository.save(product);
    }

    @Override
    public ProductEntity updateProduct(long id, ProductDTO productDTO) {
        CategoryEntity category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("Category with ID " + productDTO.getCategoryId() + " not found"));
        ProductEntity product = getProductById(id);
        product.setName(productDTO.getProductName());
        product.setDescription(productDTO.getProductDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantityInStock(productDTO.getQuantityInStock());
        product.setCategory(category);
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
        System.out.println(size);
        if(size >= 4){
            throw new IllegalArgumentException("Product image is more than 4");
        }
        return productImageRepository.save(productImage);
    }

    @Override
    public void deleteProductImage(long imageId) {
        productImageRepository.deleteById(imageId);
    }

    @Override
    public List<ProductColorEntity> getAllProductColors(long productId) {
        ProductEntity product = getProductById(productId);
        return productColorRepository.findByProduct_Id(product.getId());
    }

    @Override
    public ProductColorEntity getProductColorById(long colorId) {
        return productColorRepository.findById(colorId)
                .orElseThrow(() -> new IllegalArgumentException("Product color with ID " + colorId + " not found"));
    }

    @Override
    public ProductColorEntity saveProductColor(long productId, ProductColorDTO productColorDTO) {
        ProductEntity product = getProductById(productId);

        boolean colorExists = productColorRepository.findByProduct_IdAndColorName(productId, productColorDTO.getColorName()).isPresent();
        if (colorExists) {
            throw new IllegalArgumentException("Color " + productColorDTO.getColorName() + " already exists for product ID " + productId);
        }

        ProductColorEntity productColor = ProductColorEntity.builder()
                .product(product)
                .colorName(productColorDTO.getColorName())
                .build();
        return productColorRepository.save(productColor);
    }

    @Override
    public ProductColorEntity updateProductColor(long colorId, ProductColorDTO productColorDTO) {
        ProductColorEntity productColor = getProductColorById(colorId);
        ProductEntity product = productColor.getProduct();

        boolean colorExists = productColorRepository.findByProduct_IdAndColorName(product.getId(), productColorDTO.getColorName()).isPresent();
        if (colorExists) {
            throw new IllegalArgumentException("Color " + productColorDTO.getColorName() + " already exists for product ID " + product.getId());
        }

        productColor.setColorName(productColorDTO.getColorName());
        return productColorRepository.save(productColor);
    }

    @Override
    public void deleteProductColor(long colorId) {
        productColorRepository.deleteById(colorId);
    }
}
