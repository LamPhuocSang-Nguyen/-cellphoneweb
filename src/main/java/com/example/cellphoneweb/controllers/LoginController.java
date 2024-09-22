package com.example.cellphoneweb.controllers;

import com.example.cellphoneweb.dtos.LoginDTO;
import com.example.cellphoneweb.responses.ApiReponse;
import com.example.cellphoneweb.services.LoginServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginServiceImp loginServiceImp;

    @PostMapping("test")
    public ResponseEntity<ApiReponse> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) throws JsonProcessingException {
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

        return ResponseEntity.ok(loginServiceImp.loginWithUserNameAndPassword(loginDTO.getUsername(),loginDTO.getPassword()));
    }
}
