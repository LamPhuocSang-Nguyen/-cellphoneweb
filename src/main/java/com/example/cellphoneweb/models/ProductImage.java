package com.example.cellphoneweb.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImage {
    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(name = "image_url")
    private String imageUrl;
}
