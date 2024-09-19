package com.example.cellphoneweb.repositorise;

import com.example.cellphoneweb.models.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    List<Voucher> findByVoucherCode(String voucherCode);
}
