package com.example.cellphoneweb.controllers;


import com.example.cellphoneweb.dtos.ProductDTO;
import com.example.cellphoneweb.exceptions.ResourceNotFoundException;
import com.example.cellphoneweb.models.ProductEntity;
import com.example.cellphoneweb.responses.ApiResponse;
import com.example.cellphoneweb.responses.ProductListResponse;
import com.example.cellphoneweb.responses.ProductResponse;
import com.example.cellphoneweb.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${api.prefix}/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
        Page<ProductEntity> productResponses= productService.getProducts(pageable);
        int totalPage = productResponses.getTotalPages();
        List<ProductResponse> responseList= productResponses.getContent().stream()
                .map(product -> ProductResponse.fromProduct(product))
                .toList();

        ProductListResponse productListResponse = ProductListResponse.builder()
                .productResponsesList(responseList)
                .totalPages(totalPage)
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Show students sucessfully")
                .data(productListResponse) // List of students
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAll(){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(productService.getAllProducts())
                .status(HttpStatus.OK.value())
                .message("OK")
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<ApiResponse> addingProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        ProductEntity product = productService.saveProduct(productDTO);

        ApiResponse apiResponse = ApiResponse.builder()
                .data(product)
                .message("Insert sucessfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/admin/edit/{id}")
    public ResponseEntity<ApiResponse> editProduct(@PathVariable long id, @Valid @RequestBody ProductDTO productDTO, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        ProductEntity product = productService.updateProduct(id,productDTO);
        if(product == null){
            throw new ResourceNotFoundException("Product không tìm thấy với id "+ id);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(product)
                .message("Updated sucessfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable long id){
        ProductEntity product = productService.getProductById(id);
        if(product == null){
            throw new ResourceNotFoundException("Product không tìm thấy với id "+ id);
        }
        productService.deleteProduct(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(id)
                .message("Delete sucessfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
