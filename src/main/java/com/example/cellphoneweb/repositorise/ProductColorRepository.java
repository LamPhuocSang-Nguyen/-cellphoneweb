package com.example.cellphoneweb.repositorise;

import com.example.cellphoneweb.models.ProductColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductColorRepository extends JpaRepository<ProductColorEntity, Long> {
    List<ProductColorEntity> findByProduct_Id(Long productId);
    void deleteById(Long id);
    Optional<ProductColorEntity> findByProduct_IdAndColorName(long productId, String colorName);
}
