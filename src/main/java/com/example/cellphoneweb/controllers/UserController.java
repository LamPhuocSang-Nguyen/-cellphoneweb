package com.example.cellphoneweb.controllers;

import com.example.cellphoneweb.dtos.UserDTO;
import com.example.cellphoneweb.models.UserEntity;
import com.example.cellphoneweb.responses.ApiResponse;
import com.example.cellphoneweb.services.EmailService;
import com.example.cellphoneweb.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse>  register(@Valid @RequestBody UserDTO  userDTO, BindingResult result) throws MessagingException {
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            ApiResponse apiReponse = ApiResponse.builder()
                    .data(errors)
                    .message("Error")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.badRequest().body(apiReponse);

        }
        UserEntity user = userService.register(userDTO);
        ApiResponse apiReponse = ApiResponse.builder()
                .data(user)
                .message("Successfull")
                .status(HttpStatus.OK.value())
                .build();


        emailService.sendEmail(userDTO.getEmail(),userDTO.getUsername(),"mail","http://localhost:8080/user/activate?user="+user.getUsername() + "&code=" + "12345","12345","Subject");

        return  ResponseEntity.ok(apiReponse);
    }

    @PostMapping("/activate")
    public ResponseEntity<ApiResponse>  activate(@RequestParam(name = "user") String username, @RequestParam(name = "code") String verifyCode){
        if (verifyCode == "12345"){
            userService.activate(username);
            return ResponseEntity.ok(ApiResponse.builder()
                            .message("Tai khoan da kich hoat")
                    .build());
        }
        return  null;
    }
}
