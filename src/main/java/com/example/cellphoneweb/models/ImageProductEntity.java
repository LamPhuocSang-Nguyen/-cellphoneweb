//package com.example.cellphoneweb.models;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "product_images")
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class ImageProductEntity extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id", nullable = false)
//    private ProductEntity product;
//
//    @Column(name = "img_url")
//    private String imageUrl;
//}
