package com.example.cellphoneweb.dtos;

import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderRequestDTO {
    @Valid
    private OrderDTO orderDTO;
    private List<Long> productIds;
    private List<Integer> quantities;
    private List<String> colors;
}
