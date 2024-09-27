package com.example.cellphoneweb.controllers;

import com.example.cellphoneweb.dtos.CategoryDTO;
import com.example.cellphoneweb.models.CategoryEntity;
import com.example.cellphoneweb.responses.ApiResponse;
import com.example.cellphoneweb.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/admin/categories")
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<CategoryEntity> categories = categoryService.getAllCategories();
        if(categories.isEmpty()) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Categories not found")
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(categories)
                .status(HttpStatus.OK.value())
                .message("List Categories found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/admin/categories/{category_Id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable long category_Id) {
        CategoryEntity category = categoryService.getCategory(category_Id);
        if (category == null) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Category not found with ID: " + category_Id)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(category)
                .status(HttpStatus.OK.value())
                .message("Category found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/admin/categories/name/{name}")
    public ResponseEntity<ApiResponse> getCategoriesByName(@PathVariable String name) {
        List<CategoryEntity> categories = categoryService.getCategoriesByName(name);
        if(categories.isEmpty()) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Categories not found with name: " + name)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(categories)
                .status(HttpStatus.OK.value())
                .message("List Categories found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/admin/categories/create")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) {
        // Xử lý lỗi xác thực
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Validation errors occurred")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
        if(categoryService.existsByName(categoryDTO.getName())) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Category name already exists.")
                    .status(HttpStatus.CONFLICT.value())
                    .message("Conflict error")
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        }
        CategoryEntity category = categoryService.createCategory(categoryDTO);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(category)
                .status(HttpStatus.OK.value())
                .message("Category created successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("/admin/categories/update/{category_Id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable long category_Id, @Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) {
        // Xử lý lỗi xác thực
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Validation errors occurred")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
        CategoryEntity existingCategory = categoryService.getCategory(category_Id);
        if (existingCategory == null) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Category not found with ID: " + category_Id)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        CategoryEntity category = categoryService.updateCategory(category_Id, categoryDTO);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(category)
                .status(HttpStatus.OK.value())
                .message("Category updated successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/admin/categories/delete/{category_Id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable long category_Id) {
        CategoryEntity category = categoryService.getCategory(category_Id);
        if (category == null) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Category not found with ID: " + category_Id)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        categoryService.deleteCategory(category_Id);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(category)
                .status(HttpStatus.OK.value())
                .message("Category deleted successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
