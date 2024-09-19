package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.VoucherDTO;
import com.example.cellphoneweb.models.Voucher;
import com.example.cellphoneweb.repositorise.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherService implements IVoucherService {
    public final VoucherRepository voucherRepository;

    @Override
    public Voucher createVoucher(VoucherDTO voucherDTO) {
        Voucher vch = Voucher.builder()
                .voucherCode(voucherDTO.getVoucherCode())
                .discount(voucherDTO.getDiscount())
                .voucher_status(voucherDTO.getVoucher_status())
                .build();
        return voucherRepository.save(vch);
    }

    @Override
    public Voucher updateVoucher(int voucher_Id, VoucherDTO voucherDTO) {
        Voucher vch = voucherRepository.findById(voucher_Id).orElse(null);
        if(vch != null){
            vch.setVoucherCode(voucherDTO.getVoucherCode());
            vch.setDiscount(voucherDTO.getDiscount());
            vch.setVoucher_status(voucherDTO.getVoucher_status());
            return voucherRepository.save(vch);
        }
        return null;
    }

    @Override
    public Voucher deleteVoucher(int voucher_Id) {
        Voucher vch = voucherRepository.findById(voucher_Id).orElse(null);
        if(vch != null){
            voucherRepository.delete(vch);
            return vch;
        }
        return null;
    }

    @Override
    public Voucher getVoucher(int voucher_Id) {
        return voucherRepository.findById(voucher_Id).orElse(null);
    }

    @Override
    public List<Voucher> getVoucherByCode(String voucherCode) {
        return voucherRepository.findByVoucherCode(voucherCode);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

}
