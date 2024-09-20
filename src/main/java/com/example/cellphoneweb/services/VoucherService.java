package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.VoucherDTO;
import com.example.cellphoneweb.models.VoucherEntity;
import com.example.cellphoneweb.repositorise.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherService implements IVoucherService {
    public final VoucherRepository voucherRepository;

    @Override
    public VoucherEntity createVoucher(VoucherDTO voucherDTO) {
        VoucherEntity vch = VoucherEntity.builder()
                .code(voucherDTO.getCode())
                .discountAmount(voucherDTO.getDiscountAmount())
                .expirationDate(voucherDTO.getExpirationDate())
                .isActive(voucherDTO.getIsActive())
                .minOrderValue(voucherDTO.getMinOrderValue())
                .build();
        return voucherRepository.save(vch);
    }

    @Override
    public VoucherEntity updateVoucher(long voucher_Id, VoucherDTO voucherDTO) {
        VoucherEntity vch = voucherRepository.findById(voucher_Id).orElse(null);
        if(vch != null){
            vch.setCode(voucherDTO.getCode());
            vch.setDiscountAmount(voucherDTO.getDiscountAmount());
            vch.setExpirationDate(voucherDTO.getExpirationDate());
            vch.setIsActive(voucherDTO.getIsActive());
            vch.setMinOrderValue(voucherDTO.getMinOrderValue());
            return voucherRepository.save(vch);
        }
        return null;
    }

    @Override
    public VoucherEntity deleteVoucher(long voucher_Id) {
        VoucherEntity vch = voucherRepository.findById(voucher_Id).orElse(null);
        if(vch != null){
            voucherRepository.delete(vch);
            return vch;
        }
        return null;
    }

    @Override
    public VoucherEntity getVoucher(long voucher_Id) {
        return voucherRepository.findById(voucher_Id).orElse(null);
    }

    @Override
    public List<VoucherEntity> getVoucherByCode(String code) {
        return voucherRepository.findByCode(code);
    }

    @Override
    public List<VoucherEntity> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public boolean existsByCode(String code) {
        return voucherRepository.existsByCode(code);
    }
}
