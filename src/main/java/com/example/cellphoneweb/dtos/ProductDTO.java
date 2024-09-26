package com.example.cellphoneweb.dtos;

import com.example.cellphoneweb.models.CategoryEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    @NotBlank(message = "Tên sản phâm không được để trống")
    @Size(min = 2 ,max = 50, message = "Tên sản phẩm phải có từ 2 đến 50 ký tự")
    private String productName;

    @NotBlank(message = "Trường mô tả sản phẩm không được để trống")
    private String productDescription;

    @NotNull(message = "Giá của sản phẩm không được bỏ trống")
    private BigDecimal price;

    @NotNull(message = "Số lượng trong kho không được để trống")
    private int quantityInStock;

    @NotNull(message = "category khong duoc trong")
    private CategoryEntity category;

}
