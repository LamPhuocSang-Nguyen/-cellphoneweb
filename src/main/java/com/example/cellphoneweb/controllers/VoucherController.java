package com.example.cellphoneweb.controllers;

import com.example.cellphoneweb.dtos.VoucherDTO;
import com.example.cellphoneweb.models.Voucher;
import com.example.cellphoneweb.responses.ApiReponse;
import com.example.cellphoneweb.services.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class VoucherController {
    private final VoucherService voucherService;

    @GetMapping("/admin/vouchers")
    public ResponseEntity<ApiReponse> getAllVouchers() {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        ApiReponse apiReponse = ApiReponse.builder()
                .data(vouchers)
                .status(HttpStatus.OK.value())
                .message("List Vouchers found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiReponse);
    }
    @GetMapping("/admin/vouchers/{voucher_Id}")
    public ResponseEntity<ApiReponse> getVoucherById(@PathVariable int voucher_Id) {
        Voucher voucher = voucherService.getVoucher(voucher_Id);
        ApiReponse apiReponse = ApiReponse.builder()
                .data(voucher)
                .status(HttpStatus.OK.value())
                .message("Voucher found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiReponse);
    }
    @PostMapping("/admin/vouchers/create")
    public ResponseEntity<ApiReponse> createVoucher(@Valid @RequestBody VoucherDTO voucherDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            ApiReponse apiReponse = ApiReponse.builder()
                    .data(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Validation errors occurred")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiReponse);
        }
        Voucher vch = voucherService.createVoucher(voucherDTO);
        ApiReponse apiReponse = ApiReponse.builder()
                .data(vch)
                .status(HttpStatus.OK.value())
                .message("Voucher created successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiReponse);
    }
    @PutMapping("/admin/vouchers/update/{voucher_Id}")
    public ResponseEntity<ApiReponse> updateVoucher(@PathVariable int voucher_Id, @Valid @RequestBody VoucherDTO voucherDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            ApiReponse apiReponse = ApiReponse.builder()
                    .data(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Validation errors occurred")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiReponse);
        }
        Voucher vch = voucherService.updateVoucher(voucher_Id, voucherDTO);
        ApiReponse apiReponse = ApiReponse.builder()
                .data(vch)
                .status(HttpStatus.OK.value())
                .message("Voucher updated successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiReponse);
    }
    @DeleteMapping("/admin/vouchers/delete/{voucher_Id}")
    public ResponseEntity<ApiReponse> deleteVoucher(@PathVariable int voucher_Id) {
        Voucher vch = voucherService.deleteVoucher(voucher_Id);
        ApiReponse apiReponse = ApiReponse.builder()
                .data(vch)
                .status(HttpStatus.OK.value())
                .message("Voucher deleted successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiReponse);
    }
    @GetMapping("/admin/vouchers/code/{voucherCode}")
    public ResponseEntity<ApiReponse> getVoucherByCode(@PathVariable String voucherCode) {
        List<Voucher> vouchers = voucherService.getVoucherByCode(voucherCode);
        ApiReponse apiReponse = ApiReponse.builder()
                .data(vouchers)
                .status(HttpStatus.OK.value())
                .message("Voucher found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiReponse);
    }
}
