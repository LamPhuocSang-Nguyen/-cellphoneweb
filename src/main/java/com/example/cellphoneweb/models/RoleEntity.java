package com.example.cellphoneweb.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "roles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<UserEntity> users;
}
