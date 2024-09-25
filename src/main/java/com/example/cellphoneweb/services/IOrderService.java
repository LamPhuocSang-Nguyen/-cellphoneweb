package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.OrderDTO;
import com.example.cellphoneweb.dtos.OrderDetailDTO;
import com.example.cellphoneweb.models.OrderDetailEntity;
import com.example.cellphoneweb.models.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    OrderEntity getOrderById(long id);
    List<OrderEntity> getAllOrders();
    OrderEntity saveOrder(OrderDTO orderDTO, List<Long> productIds, List<Integer> quantities, List<String> colors);
    OrderEntity updateOrder(long id, OrderDTO orderDTO);
    void deleteOrder(long id);
    Page<OrderEntity> getOrders(Pageable pageable);

    List<OrderDetailEntity> getOrderDetailsByOrderId(long orderId);
    List<OrderDetailEntity> saveOrderDetails(long orderId, List<Long> productIds, List<Integer> quantities, List<String> colors);
    OrderDetailEntity updateOrderDetail(long orderDetailId, OrderDetailDTO orderDetailDTO);
    OrderDetailEntity getOrderDetailById(long orderDetailId);
}
