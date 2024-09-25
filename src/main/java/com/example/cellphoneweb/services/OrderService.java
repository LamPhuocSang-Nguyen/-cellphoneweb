package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.OrderDTO;
import com.example.cellphoneweb.dtos.OrderDetailDTO;
import com.example.cellphoneweb.models.OrderDetailEntity;
import com.example.cellphoneweb.models.OrderEntity;
import com.example.cellphoneweb.models.ProductEntity;
import com.example.cellphoneweb.repositorise.OrderDetailRepository;
import com.example.cellphoneweb.repositorise.OrderRepository;
import com.example.cellphoneweb.repositorise.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderEntity getOrderById(long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderEntity saveOrder(OrderDTO orderDTO, List<Long> productIds, List<Integer> quantities, List<String> colors) {
        OrderEntity orderEntity = OrderEntity.builder()
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
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        List<OrderDetailEntity> orderDetails = saveOrderDetails(savedOrder.getId(), productIds, quantities, colors);
        savedOrder.setOrderDetails(new HashSet<>(orderDetails));
        return orderRepository.save(savedOrder);
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

    @Override
    public List<OrderDetailEntity> getOrderDetailsByOrderId(long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public List<OrderDetailEntity> saveOrderDetails(long orderId, List<Long> productIds, List<Integer> quantities, List<String> colors) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn hàng với ID: " + orderId));

        List<OrderDetailEntity> orderDetails = new ArrayList<>();

        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            Integer quantity = quantities.get(i);
            String color = colors.get(i);

            ProductEntity product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + productId));

            OrderDetailEntity orderDetail = OrderDetailEntity.builder()
                    .order(order)
                    .product(product)
                    .quantity(quantity)
                    .color(color)
                    .build();

            orderDetails.add(orderDetailRepository.save(orderDetail));
        }
        return orderDetails;
    }

    @Override
    public OrderDetailEntity updateOrderDetail(long orderDetailId, OrderDetailDTO orderDetailDTO) {
        OrderDetailEntity existingOrderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new IllegalArgumentException("Not found order detail with id: " + orderDetailId));
        if (orderDetailDTO.getColor() != null) {
            existingOrderDetail.setColor(orderDetailDTO.getColor());
        }
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public OrderDetailEntity getOrderDetailById(long orderDetailId) {
        return orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new IllegalArgumentException("Not found order detail with id: " + orderDetailId));

    }
}
