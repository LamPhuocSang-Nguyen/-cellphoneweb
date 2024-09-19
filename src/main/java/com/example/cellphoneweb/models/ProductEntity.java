package com.example.cellphoneweb.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_description")
    private String description;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "quantity_in_stock")
    private long quantityInStock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true) // Mối quan hệ 1-n với OrderDetailEntity
    private Set<OrderDetailEntity> orderDetails;

    @OneToMany(mappedBy = "productImg", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ImageProductEntity> imageProductEntities;

    @OneToMany(mappedBy = "productCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartEntity> carts;
}
