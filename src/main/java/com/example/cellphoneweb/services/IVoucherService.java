package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.VoucherDTO;
import com.example.cellphoneweb.models.Voucher;

import java.util.List;

public interface IVoucherService {
    Voucher createVoucher(VoucherDTO voucherDTO);
    Voucher updateVoucher(int voucher_Id, VoucherDTO voucherDTO);
    Voucher deleteVoucher(int voucher_Id);
    Voucher getVoucher(int voucher_Id);
    List<Voucher> getAllVouchers();
    List<Voucher> getVoucherByCode(String voucherCode);

}
