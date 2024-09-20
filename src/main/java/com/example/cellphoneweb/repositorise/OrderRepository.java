package com.example.cellphoneweb.repositorise;

import com.example.cellphoneweb.models.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
