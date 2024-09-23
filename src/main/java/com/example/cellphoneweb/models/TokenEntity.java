//package com.example.cellphoneweb.models;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.Fetch;
//
//@Entity
//@Table(name = "token")
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class TokenEntity extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private UserEntity user;
//
//    @Column(name = "access_token")
//    private String acessToken;
//
//    @Column(name = "refreshToken")
//    private String refresToken;
//
//    @Column(name = "reset_token")
//    private String resetToken;
//
//}
