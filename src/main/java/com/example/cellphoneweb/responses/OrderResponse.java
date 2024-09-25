package com.example.cellphoneweb.responses;

import java.util.Date;

import com.example.cellphoneweb.models.OrderEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long OrderId;
    private String address;
    private String note;
    private Date orderDate;
    private Float totalMoney;
    private String shippingMethod;
    private String shippingAddress;
    private Date shippingDate;
    private String trackingNumber;
    private String paymentMethod;


    public static OrderResponse fromOrder(OrderEntity orderEntity){
        OrderResponse orderResponse = OrderResponse.builder()
                        .OrderId(orderEntity.getId())
                        .address(orderEntity.getAddress())
                        .note(orderEntity.getNote())
                        .orderDate(orderEntity.getOrderDate())
                        .totalMoney(orderEntity.getTotalMoney())
                        .shippingMethod(orderEntity.getShippingMethod())
                        .shippingAddress(orderEntity.getShippingAddress())
                        .shippingDate(orderEntity.getShippingDate())
                        .trackingNumber(orderEntity.getTrackingNumber())
                        .paymentMethod(orderEntity.getPaymentMethod())
                        .build();
        return orderResponse;
    }
    
}
