package com.example.cellphoneweb.repositorise;

import com.example.cellphoneweb.models.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findByName(String name);
    boolean existsByName(String name);
}
