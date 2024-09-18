package com.example.cellphoneweb.responses;


import lombok.*;

import java.util.List;

@Builder
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductListRespone {
    private List<ProductResponse> productResponsesList;
    private int totalPages;
}
