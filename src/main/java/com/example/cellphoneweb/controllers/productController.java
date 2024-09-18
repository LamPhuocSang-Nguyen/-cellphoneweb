package com.example.cellphoneweb.controllers;


import com.example.cellphoneweb.dtos.ProductDTO;
import com.example.cellphoneweb.exceptions.ResourceNotFoundException;
import com.example.cellphoneweb.models.Product;
import com.example.cellphoneweb.responses.ApiReponse;
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
public class productController {
    private final ProductService productService;


    @GetMapping("/list")
    public ResponseEntity<ApiReponse> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Product> productResponses= productService.getProducts(pageable);
        int totalPage = productResponses.getTotalPages();
        List<ProductResponse> responseList= productResponses.getContent().stream()
                .map(product -> ProductResponse.fromProduct(product))
                .toList();

        ProductListResponse productListResponse = ProductListResponse.builder()
                .productResponsesList(responseList)
                .totalPages(totalPage)
                .build();

        ApiReponse apiReponse = ApiReponse.builder()
                .status(HttpStatus.OK.value())
                .message("Show students sucessfully")
                .data(productListResponse) // List of students
                .build();

        return ResponseEntity.ok(apiReponse);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiReponse> getAll(){
        ApiReponse apiReponse = ApiReponse.builder()
                .data(productService.getAllProducts())
                .status(HttpStatus.OK.value())
                .message("OK")
                .build();

        return ResponseEntity.ok().body(apiReponse);
    }


    @PostMapping("/admin/add")
    public ResponseEntity<ApiReponse> addingProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            ApiReponse apiReponse = ApiReponse.builder()
                    .data(errors)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.badRequest().body(apiReponse);
        }
        Product product = productService.saveProduct(productDTO);

        ApiReponse apiReponse = ApiReponse.builder()
                .data(product)
                .message("Insert sucessfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(apiReponse);
    }

    @PutMapping("/admin/edit/{id}")
    public ResponseEntity<ApiReponse> editProduct(@PathVariable int id, @Valid @RequestBody ProductDTO productDTO, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            ApiReponse apiReponse = ApiReponse.builder()
                    .data(errors)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();

            return ResponseEntity.badRequest().body(apiReponse);
        }
        Product product = productService.updateProduct(id,productDTO);
        if(product == null){
            throw new ResourceNotFoundException("Product khong tim thay voi id "+ id);
        }

        ApiReponse apiResponse = ApiReponse.builder()
                .data(product)
                .message("Updated sucessfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }


    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<ApiReponse> delete(@PathVariable int id){
        Product product = productService.getProductById(id);
        if(product == null){
            throw new ResourceNotFoundException("Product khong tim thay voi id "+ id);
        }
        productService.deleteProduct(id);
        ApiReponse apiReponse = ApiReponse.builder()
                .data(id)
                .message("Delete sucessfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiReponse);
    }
}
