package com.example.cellphoneweb.responses;

import com.example.cellphoneweb.models.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse extends BaseResponse {
    private int user_Id;
    private String user_name;
    private String password;
    private String email;
    private String address;
    private String phone;

    public static UserResponse fromUser(User user) {
        UserResponse userResponse = UserResponse.builder()
                .user_Id(user.getUser_Id())
                .user_name(user.getUser_name())
                .password(user.getPassword())
                .email(user.getEmail())
                .address(user.getAddress())
                .phone(user.getPhone())
                .build();
        userResponse.setCreated_at(user.getCreated_at());
        userResponse.setUpdated_at(user.getUpdated_at());
        return userResponse;
    }
}
