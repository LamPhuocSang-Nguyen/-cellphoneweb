package com.example.cellphoneweb.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "vouchers")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false)
    private String code; // Mã voucher

    @Column(name = "discount_amount", nullable = false)
    private BigDecimal discountAmount; // Số tiền hoặc phần trăm giảm giá

    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expirationDate; // Ngày hết hạn

    @Column(name = "is_active", columnDefinition = "TINYINT(1)")
    private Boolean isActive; // Trạng thái hoạt động

    @Column(name = "min_order_value")
    private BigDecimal minOrderValue; // Giá trị đơn hàng tối thiểu

}