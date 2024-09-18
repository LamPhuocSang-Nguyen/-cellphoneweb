package com.example.cellphoneweb.services;

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
    public Voucher createVoucher(Voucher voucher) {
        Voucher vch = Voucher.builder()
                .voucher_code(voucher.getVoucher_code())
                .discount(voucher.getDiscount())
                .voucher_status(voucher.getVoucher_status())
                .build();
        return voucherRepository.save(vch);
    }

    @Override
    public Voucher updateVoucher(int voucher_Id, Voucher voucher) {
        Voucher vch = voucherRepository.findById(voucher_Id).orElse(null);
        if(vch != null){
            vch.setVoucher_code(voucher.getVoucher_code());
            vch.setDiscount(voucher.getDiscount());
            vch.setVoucher_status(voucher.getVoucher_status());
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
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

}
