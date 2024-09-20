package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.VoucherDTO;
import com.example.cellphoneweb.models.VoucherEntity;

import java.util.List;

public interface IVoucherService {
    VoucherEntity createVoucher(VoucherDTO voucherDTO);
    VoucherEntity updateVoucher(long voucher_Id, VoucherDTO voucherDTO);
    VoucherEntity deleteVoucher(long voucher_Id);
    VoucherEntity getVoucher(long voucher_Id);
    boolean existsByCode(String code);
    List<VoucherEntity> getAllVouchers();
    List<VoucherEntity> getVoucherByCode(String voucherCode);

}
