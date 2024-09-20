package com.example.cellphoneweb.repositorise;

import com.example.cellphoneweb.models.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity,Long> {
}
