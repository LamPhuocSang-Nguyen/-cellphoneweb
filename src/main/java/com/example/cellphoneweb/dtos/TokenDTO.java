package com.example.cellphoneweb.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {
    private String accessToken;  // Sửa lỗi chính tả từ acessToken thành accessToken
    private String refreshToken;  // Sửa lỗi chính tả từ refresToken thành refreshToken
    private String resetToken;
    private Long userId;  //
}
