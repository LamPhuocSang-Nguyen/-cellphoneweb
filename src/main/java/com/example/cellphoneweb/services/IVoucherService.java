package com.example.cellphoneweb.services;

import com.example.cellphoneweb.models.Voucher;

import java.util.List;

public interface IVoucherService {
    Voucher createVoucher(Voucher voucher);
    Voucher updateVoucher(int voucher_Id, Voucher voucher);
    Voucher deleteVoucher(int voucher_Id);
    Voucher getVoucher(int voucher_Id);
    List<Voucher> getAllVouchers();

}
