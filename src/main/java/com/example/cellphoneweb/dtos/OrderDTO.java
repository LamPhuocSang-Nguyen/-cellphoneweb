package com.example.cellphoneweb.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDTO {
    @JsonProperty("address")
    @NotBlank(message = "dia chi sản phâm không được để trống")
    @Size(min = 2 ,max = 50, message = "Tên sản phẩm phải có từ 2 đến 50 ký tự")
    private String address;

    @JsonProperty("note")
    @NotBlank(message = "Trường mô tả sản phẩm không được để trống")
    private String note;

    @JsonProperty("orderDate")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Past(message = "Phai la 1 ngay trong qua khu")
    private Date orderDate;

    @JsonProperty("totalMoney")
    @NotNull(message = "Giá của sản phẩm không được bỏ trống")
    private Float totalMoney;

    @JsonProperty("shippingMethod")
    @NotBlank(message = "Phuong thuc van chuyen khong duoc trong")
    private String shippingMethod;

    @JsonProperty("shippingAddress")
    @NotBlank(message = "dia chi van chuyen khong duoc trong")
    private String shippingAddress;

    @JsonProperty("shippingDate")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date shippingDate;

    @JsonProperty("trackingNumber")
    @NotBlank(message = "tracking khong duoc trong")
    private String trackingNumber;

    @JsonProperty("paymentMethod")
    @NotBlank(message = "phuong thuc thanh thanh toan khong duoc trong")
    private String paymentMethod;

}
