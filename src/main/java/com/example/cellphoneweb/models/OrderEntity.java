package com.example.cellphoneweb.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // @ManyToOne
    // @JoinColumn(name = "user_id", nullable = false)
    // private UserEntity user;

    @Column(nullable = false)
    private String address;

    private String note;

    @Column(name = "order_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_money")
    private Float totalMoney;

    private String shippingMethod;
    private String shippingAddress;
    private Date shippingDate;
    private String trackingNumber;
    private String paymentMethod;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean active;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetailEntity> orderDetails;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InvoiceEntity> invoices; // Mối quan hệ 1-n với InvoiceEntity

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private VoucherEntity voucher; // Mối quan hệ với VoucherEntity
}
