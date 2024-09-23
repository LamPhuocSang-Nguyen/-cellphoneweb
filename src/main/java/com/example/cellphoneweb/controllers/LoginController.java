package com.example.cellphoneweb.controllers;

import com.example.cellphoneweb.dtos.LoginDTO;
import com.example.cellphoneweb.responses.ApiResponse;
import com.example.cellphoneweb.services.LoginServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.REFERER;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginServiceImp loginServiceImp;

    @PostMapping("/test")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) throws JsonProcessingException {
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

        return ResponseEntity.ok(loginServiceImp.loginWithUserNameAndPassword(loginDTO.getUsername(),loginDTO.getPassword()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> login(HttpServletRequest request) {
        final String refreshToken = request.getHeader(REFERER);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("OK")
                .data(loginServiceImp.refreshLogin(refreshToken))
                .status(HttpStatus.OK.value())
                .build();
        return  ResponseEntity.ok(apiResponse);
    }
}
