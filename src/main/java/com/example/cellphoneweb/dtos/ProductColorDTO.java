package com.example.cellphoneweb.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductColorDTO {

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product ID must be greater than 0")
    private long productId;

    @JsonProperty("color_name")
    @NotBlank(message = "Color name is not blank")
    @Size(min = 3, max = 50, message = "Color names must be between 3 and 50 characters in length")
    private String colorName;
}
