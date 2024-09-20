package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.CategoryDTO;
import com.example.cellphoneweb.models.CategoryEntity;

import java.util.List;

public interface  ICategoryService {
    CategoryEntity createCategory(CategoryDTO categoryDTO);
    CategoryEntity updateCategory(long category_Id, CategoryDTO categoryDTO);
    CategoryEntity deleteCategory(long category_Id);
    CategoryEntity getCategory(long category_Id);
    boolean existsByName(String name);
    List<CategoryEntity> getAllCategories();
    List<CategoryEntity> getCategoriesByName(String name);
}
