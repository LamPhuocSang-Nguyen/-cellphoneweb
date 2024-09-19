package com.example.cellphoneweb.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherDTO {
    @JsonProperty("voucherCode")
    @NotBlank(message = "voucher_code is required")
    @Length(min = 3, max = 20, message = "voucherCode must be between 3 and 20 characters")
    private String voucherCode;
    @JsonProperty("discount")
    @NotBlank(message = "discount is required")
    @Length(min = 3, max = 20, message = "discount must be between 3 and 20 characters")
    private String discount;
    @JsonProperty("voucher_status")
    @NotBlank(message = "voucher_status is required")
    @Length(min = 3, max = 20, message = "voucher_status must be between 3 and 20 characters")
    private String voucher_status;
}
