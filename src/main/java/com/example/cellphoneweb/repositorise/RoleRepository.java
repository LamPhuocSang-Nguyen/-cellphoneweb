package com.example.cellphoneweb.repositorise;

import com.example.cellphoneweb.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findByName(String name);

    boolean existsByName(String name);
}
