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
        OrderEntity orderEntity = OrderEntity
                .builder()
                .address(orderDTO.getAddress())
                .note(orderDTO.getNote())
                .orderDate(orderDTO.getOrderDate())
                .totalMoney(orderDTO.getTotalMoney())
                .shippingMethod(orderDTO.getShippingMethod())
                .shippingAddress(orderDTO.getShippingAddress())
                .shippingDate((orderDTO.getShippingDate()))
                .trackingNumber(orderDTO.getTrackingNumber())
                .paymentMethod(orderDTO.getPaymentMethod())
                .build();

        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderEntity updateOrder(long id, OrderDTO orderDTO) {
        OrderEntity orderEntity = getOrderById(id);
        orderEntity.setAddress(orderDTO.getAddress());
        orderEntity.setNote(orderDTO.getNote());
        orderEntity.setOrderDate(orderDTO.getOrderDate());
        orderEntity.setTotalMoney(orderDTO.getTotalMoney());
        orderEntity.setShippingMethod(orderDTO.getShippingMethod());
        orderEntity.setShippingAddress(orderDTO.getShippingAddress());
        orderEntity.setShippingDate(orderDTO.getShippingDate());
        orderEntity.setTrackingNumber(orderDTO.getTrackingNumber());
        orderEntity.setPaymentMethod(orderDTO.getPaymentMethod());

        return orderRepository.save(orderEntity);
    }

    @Override
    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Page<OrderEntity> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}
