package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.OrderDTO;
import com.example.cellphoneweb.models.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    OrderEntity getOrderById(long id);
    List<OrderEntity> getAllOrders();
    OrderEntity saveOrder(OrderDTO orderDTO);
    OrderEntity updateOrder(long id, OrderDTO orderDTO);
    void deleteOrder(long id);
    Page<OrderEntity> getOrders(Pageable pageable);
}
