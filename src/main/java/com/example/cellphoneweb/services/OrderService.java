package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.OrderDTO;
import com.example.cellphoneweb.exceptions.ConflictException;
import com.example.cellphoneweb.models.OrderEntity;
import com.example.cellphoneweb.models.OrderStatus;
import com.example.cellphoneweb.models.UserEntity;
import com.example.cellphoneweb.repositorise.OrderRepository;
import com.example.cellphoneweb.repositorise.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

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
        UserEntity userEntity = userRepository.findById(orderDTO.getUserId()).orElse(null);
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
                .user(userEntity)
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
        OrderEntity orderEntity =orderRepository.findById(id).orElse(null);
        if(orderEntity!=null && orderEntity.getStatus() == OrderStatus.PENDING){
            orderRepository.deleteById(id);
        }else if(orderEntity!=null){
            throw new ConflictException("Can not canceled because the order status is: " + orderEntity.getStatus());
        }
    }

    @Override
    public Page<OrderEntity> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}
