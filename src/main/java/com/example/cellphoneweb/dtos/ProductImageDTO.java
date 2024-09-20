package com.example.cellphoneweb.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {
    @JsonProperty("product_id")
    @Min(value = 1, message = "Id product phải lớn hơn 0")
    private long productId;
    @Size(min=5, max = 200, message = "Tên hình ảnh phải lớn hơn 5 kí tự và không quá 200 kí tự")
    @JsonProperty("img_url")
    private String imageUrl;
}
