// package com.example.cellphoneweb.services;

// import com.example.cellphoneweb.dtos.ProductImageDTO;
// import com.example.cellphoneweb.models.ImageProductEntity;
// import com.example.cellphoneweb.models.ProductEntity;
// import com.example.cellphoneweb.repositorise.ProductImageRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// @RequiredArgsConstructor
// public class ProductImageService implements IProductImageService {
//     private final ProductImageRepository productImageRepository;
//     private final ProductService productService;

//     @Override
//     public List<ImageProductEntity> getAllProductImages(long product_id) {
//         return productImageRepository.findByProduct_Id(product_id);
//     }

//     @Override
//     public ImageProductEntity saveProductImage(long product_id, ProductImageDTO productImageDTO) {
//         ProductEntity product = productService.getProductById(product_id);
//         ImageProductEntity productImage = ImageProductEntity.builder()
//                 .product(product)
//                 .imageUrl(productImageDTO.getImageUrl())
//                 .build();
//         int size = productImageRepository.findByProduct_Id(product_id).size();
//         if(size > 4){
//             throw new IllegalArgumentException("Product image is more than 4");
//         }
//         return productImageRepository.save(productImage);
//     }

//     @Override
//     public void deleteProductImage(long imageId) {
//         productImageRepository.deleteById(imageId);
//     }
// }
