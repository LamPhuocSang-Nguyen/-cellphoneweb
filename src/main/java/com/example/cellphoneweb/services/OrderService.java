package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.OrderDTO;
import com.example.cellphoneweb.models.OrderEntity;
import com.example.cellphoneweb.repositorise.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;

    @Override
    public OrderEntity getOrderById(long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderEntity saveOrder(OrderDTO orderDTO) {
        OrderEntity orderEntity =OrderEntity
                .builder()
                .address(orderDTO.getAddress())
                .note(orderDTO.getNote())
                .orderDate(orderDTO.getOrderDate())
                .totalMoney(orderDTO.getTotalMoney())
                .shippingMethod(orderDTO.getShippingMethod())
                .shippingDate((orderDTO.getShippingDate()))
                .trackingNumber(orderDTO.getTrackingNumber())
                .paymentMethod(orderDTO.getPaymentMethod())
                .build();

        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderEntity updateOrder(long id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(long id) {

    }

    @Override
    public Page<OrderEntity> getOrders(Pageable pageable) {
        return null;
    }
}
