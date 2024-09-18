package com.example.cellphoneweb.responses;

import lombok.*;
import java.util.List;

@Builder
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse {
    private List<ProductResponse> productResponseList;
    private int totalPage;
}
