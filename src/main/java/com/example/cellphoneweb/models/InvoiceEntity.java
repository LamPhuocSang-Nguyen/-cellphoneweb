package com.example.cellphoneweb.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "invoices")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order; // Khóa ngoại tới bảng orders

    @Column(name = "invoice_number", unique = true, nullable = false)
    private String invoiceNumber; // Số hóa đơn

    @Column(name = "invoice_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate; // Ngày phát hành hóa đơn

    @Column(name = "total_amount", nullable = false)
    private Float totalAmount; // Tổng số tiền của hóa đơn

    @Column(name = "status", nullable = false)
    private String status; // Trạng thái hóa đơn

    @Column(name = "payment_method")
    private String paymentMethod; // Phương thức thanh toán

    // Không cần thiết lập mối quan hệ OneToMany đến OrderEntity
}
