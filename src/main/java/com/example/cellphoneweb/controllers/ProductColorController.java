package com.example.cellphoneweb.controllers;

import com.example.cellphoneweb.dtos.ProductColorDTO;
import com.example.cellphoneweb.models.ProductColorEntity;
import com.example.cellphoneweb.responses.ApiResponse;
import com.example.cellphoneweb.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${api.prefix}/product/color")
@RequiredArgsConstructor
public class ProductColorController {

    private final ProductService productService;

    @GetMapping("/getAllColors/{productId}")
    public ResponseEntity<ApiResponse> getAllColors(@PathVariable Long productId) {
        List<ProductColorEntity> colors = productService.getAllProductColors(productId);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(colors)
                .message("Colors fetched successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/getColor/{colorId}")
    public ResponseEntity<ApiResponse> getColorById(@PathVariable Long colorId) {
        ProductColorEntity color = productService.getProductColorById(colorId);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(color)
                .message("Color fetched successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/addColor/{productId}")
    public ResponseEntity<ApiResponse> addColor(@PathVariable Long productId, @RequestBody ProductColorDTO productColorDTO) {
        ProductColorEntity savedColor = productService.saveProductColor(productId, productColorDTO);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(savedColor)
                .message("Color added successfully")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/updateColor/{colorId}")
    public ResponseEntity<ApiResponse> updateColor(@PathVariable Long colorId, @RequestBody ProductColorDTO productColorDTO) {
        ProductColorEntity updatedColor = productService.updateProductColor(colorId, productColorDTO);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(updatedColor)
                .message("Color updated successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping("/deleteColor/{colorId}")
    public ResponseEntity<ApiResponse> deleteColor(@PathVariable Long colorId) {
        productService.deleteProductColor(colorId);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Color deleted successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
