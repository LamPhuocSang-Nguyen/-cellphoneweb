package com.example.cellphoneweb.controllers;

import com.example.cellphoneweb.dtos.UserDTO;
import com.example.cellphoneweb.responses.ApiReponse;
import com.example.cellphoneweb.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiReponse>  register(@Valid @RequestBody UserDTO  userDTO, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            ApiReponse apiReponse = ApiReponse.builder()
                    .data(errors)
                    .message("Error")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.badRequest().body(apiReponse);

        }
        ApiReponse apiReponse = ApiReponse.builder()
                .data(userService.register(userDTO))
                .message("Successfull")
                .status(HttpStatus.OK.value())
                .build();

        return  ResponseEntity.ok(apiReponse);
    }
}
