package com.example.cellphoneweb.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @JsonProperty("user_name")
    @NotBlank(message = "user_name is required")
    @Length(min = 3, max = 20, message = "user_name must be between 3 and 20 characters")
    private String user_name;
    @JsonProperty("password")
    @Length(min = 8, max = 20, message = "password must be between 8 and 20 characters")
    @NotBlank(message = "password is required")
    private String password;
    @JsonProperty("email")
    @NotBlank(message = "email is required")
    private String email;
    @JsonProperty("address")
    @Length(min = 5, max = 50, message = "address must be between 5 and 50 characters")
    @NotBlank(message = "address is required")
    private String address;
    @JsonProperty("phone")
    @Length(min = 10, max = 20, message = "phone must 10 characters ")
    @NotBlank(message = "phone is required")
    private String phone;
}
