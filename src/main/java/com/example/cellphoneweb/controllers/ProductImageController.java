package com.example.cellphoneweb.controllers;

import com.example.cellphoneweb.dtos.ProductImageDTO;
import com.example.cellphoneweb.models.ImageProductEntity;
import com.example.cellphoneweb.responses.ApiResponse;
import com.example.cellphoneweb.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/${api.prefix}/product/image")
@RequiredArgsConstructor
public class ProductImageController {
    private final ProductService productService;

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        java.nio.file.Path uploadDir = Paths.get("upload");
        if(!Files.exists(uploadDir)) {
            Files.createDirectory(uploadDir);
        }
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    @GetMapping("/getAllImageStudent/{id}")
    public ResponseEntity<ApiResponse> getAllImage(@PathVariable Long id) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(productService.getAllProductImages(id))
                .message("Search success")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping(value = "/uploads/{id}")
    public ResponseEntity<ApiResponse> upLoads(@PathVariable long id, @ModelAttribute("files") List<MultipartFile> files) throws IOException {
        List<ImageProductEntity> productImages = new ArrayList<>();
        int count = 0;
        for (MultipartFile file : files) {
            if (file != null) {
                if (file.getSize() == 0) {
                    count++;
                    continue;
                }
                String fileName = storeFile(file);
                ProductImageDTO productImageDTO = ProductImageDTO.builder()
                        .imageUrl(fileName)
                        .build();
                ImageProductEntity productImage = productService.saveProductImage(id, productImageDTO);
                productImages.add(productImage);
            }
        }
        if (count == 1) {
            throw new IllegalArgumentException("Chưa chọn file");
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(productImages)
                .message("Upload success")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        try{
            java.nio.file.Path imagePath = Paths.get("upload/" + imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if(resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notFound.jpeg").toUri()));
            }
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable long imageId) {
        productService.deleteProductImage(imageId);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Image deleted successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

//    @GetMapping("/getproductid/{id}")
//    public ResponseEntity<ApiReponse> getProduct(@PathVariable long id) {
//        ApiReponse apiResponse = ApiReponse.builder()
//                .data(productService.getProductById(id))
//                .message("Search success ")
//                .status(HttpStatus.OK.value())
//                .build();
//        return ResponseEntity.ok().body(apiResponse);
//    }
}
