package com.example.cellphoneweb.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_details")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order; // Mối quan hệ n-1 với OrderEntity

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product; // Mối quan hệ n-1 với ProductEntity

    private Float price;

    @Column(name = "number_of_products")
    private Integer numberOfProducts;

    @Column(name = "total_money")
    private Float totalMoney;

    private String color;
}
