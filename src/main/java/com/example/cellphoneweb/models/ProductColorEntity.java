package com.example.cellphoneweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_color")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductColorEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private ProductEntity product;

    @Column(name = "color_name", nullable = false)
    private String colorName;
}
