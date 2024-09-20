package com.example.cellphoneweb.controllers;

import com.example.cellphoneweb.dtos.VoucherDTO;
import com.example.cellphoneweb.models.VoucherEntity;
import com.example.cellphoneweb.responses.ApiResponse;
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
    public ResponseEntity<ApiResponse> getAllVouchers() {
        List<VoucherEntity> vouchers = voucherService.getAllVouchers();
        ApiResponse apiResponse = ApiResponse.builder()
                .data(vouchers)
                .status(HttpStatus.OK.value())
                .message("List Vouchers found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @GetMapping("/admin/vouchers/{voucher_Id}")
    public ResponseEntity<ApiResponse> getVoucherById(@PathVariable int voucher_Id) {
        VoucherEntity voucher = voucherService.getVoucher(voucher_Id);
        if (voucher == null) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Voucher not found with ID: " + voucher_Id)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(voucher)
                .status(HttpStatus.OK.value())
                .message("Voucher found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PostMapping("/admin/vouchers/create")
    public ResponseEntity<ApiResponse> createVoucher(@Valid @RequestBody VoucherDTO voucherDTO, BindingResult bindingResult) {
        // Xử lý lỗi xác thực
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Validation errors occurred")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }

        // Kiểm tra xem mã voucher đã tồn tại chưa
        if (voucherService.existsByCode(voucherDTO.getCode())) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Voucher code already exists.")
                    .status(HttpStatus.CONFLICT.value())
                    .message("Conflict error")
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        }

        // Tạo voucher mới
        VoucherEntity vch = voucherService.createVoucher(voucherDTO);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(vch)
                .status(HttpStatus.OK.value())
                .message("Voucher created successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PutMapping("/admin/vouchers/update/{voucher_Id}")
    public ResponseEntity<ApiResponse> updateVoucher(@PathVariable long voucher_Id, @Valid @RequestBody VoucherDTO voucherDTO, BindingResult bindingResult) {
        // Xử lý lỗi xác thực
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()) // Lấy thông điệp lỗi rõ ràng
                    .collect(Collectors.toList());
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Validation errors occurred")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }

        // Kiểm tra xem voucher có tồn tại không
        VoucherEntity existingVoucher = voucherService.getVoucher(voucher_Id);
        if (existingVoucher == null) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Voucher not found.")
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }

        // Cập nhật voucher
        VoucherEntity updatedVoucher = voucherService.updateVoucher(voucher_Id, voucherDTO);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(updatedVoucher)
                .status(HttpStatus.OK.value())
                .message("Voucher updated successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @DeleteMapping("/admin/vouchers/delete/{voucher_Id}")
    public ResponseEntity<ApiResponse> deleteVoucher(@PathVariable int voucher_Id) {
        VoucherEntity vch = voucherService.deleteVoucher(voucher_Id);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(vch)
                .status(HttpStatus.OK.value())
                .message("Voucher deleted successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @GetMapping("/admin/vouchers/code/{code}")
    public ResponseEntity<ApiResponse> getVoucherByCode(@PathVariable String code) {
        List<VoucherEntity> vouchers = voucherService.getVoucherByCode(code);
        if (vouchers.isEmpty()) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Voucher not found with code: " + code)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Voucher not found")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(vouchers)
                .status(HttpStatus.OK.value())
                .message("Voucher found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
