package com.example.cellphoneweb.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherDTO {

    @JsonProperty("code")
    @NotBlank(message = "voucher_code is required")
    @Length(min = 3, max = 20, message = "voucherCode must be between 3 and 20 characters")
    private String code;

    @JsonProperty("discountAmount")
    @NotNull(message = "discount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount must be positive") // Kiểm tra giá trị lớn hơn 0
    private BigDecimal discountAmount;

    @JsonProperty("expirationDate")
    @NotNull(message = "expirationDate is required")
    private Date expirationDate;

    @JsonProperty("isActive")
    private Boolean isActive;

    @JsonProperty("minOrderValue")
    private BigDecimal minOrderValue;
}