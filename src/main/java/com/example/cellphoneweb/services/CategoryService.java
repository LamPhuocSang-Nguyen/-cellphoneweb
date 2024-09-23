//package com.example.cellphoneweb.services;
//
//import com.example.cellphoneweb.dtos.CategoryDTO;
//import com.example.cellphoneweb.models.CategoryEntity;
//import com.example.cellphoneweb.repositorise.CategoryRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CategoryService implements ICategoryService {
//    public final CategoryRepository categoryRepository;
//
//    @Override
//    public CategoryEntity createCategory(CategoryDTO categoryDTO) {
//        CategoryEntity category = CategoryEntity.builder()
//                .name(categoryDTO.getName())
//                .build();
//        return categoryRepository.save(category);
//    }
//
//    @Override
//    public CategoryEntity updateCategory(long category_Id, CategoryDTO categoryDTO) {
//        CategoryEntity category = categoryRepository.findById(category_Id).orElse(null);
//        if(category != null){
//            category.setName(categoryDTO.getName());
//            return categoryRepository.save(category);
//        }
//        return null;
//    }
//
//    @Override
//    public CategoryEntity deleteCategory(long category_Id) {
//        CategoryEntity category = categoryRepository.findById(category_Id).orElse(null);
//        if(category != null){
//            categoryRepository.delete(category);
//            return category;
//        }
//        return null;
//    }
//
//    @Override
//    public CategoryEntity getCategory(long category_Id) {
//        return categoryRepository.findById(category_Id).orElse(null);
//    }
//
//    @Override
//    public List<CategoryEntity> getAllCategories() {
//        return categoryRepository.findAll();
//    }
//
//    @Override
//    public List<CategoryEntity> getCategoriesByName(String name) {
//        return categoryRepository.findByName(name);
//    }
//
//    @Override
//    public boolean existsByName(String name) {
//        return categoryRepository.existsByName(name);
//    }
//
//}
