package com.example.cellphoneweb.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.cellphoneweb.responses.ApiReponse;

import java.security.InvalidParameterException;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiReponse> handleGeneralException(Exception ex, HttpServletRequest request){
        ApiReponse response = ApiReponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred" + ex.getMessage())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiReponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        ApiReponse response = ApiReponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("Resource not found " + ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ApiReponse> handleInvalidParameterException(InvalidParameterException ex){
        ApiReponse response = ApiReponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
