package com.example.cellphoneweb.controllers;

import com.example.cellphoneweb.dtos.OrderDTO;
import com.example.cellphoneweb.models.OrderEntity;
import com.example.cellphoneweb.responses.ApiResponse;
import com.example.cellphoneweb.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/${api.prefix}/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> index(){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(orderService.getAllOrders())
                .status(HttpStatus.OK.value())
                .message("OK")
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> postStudent(@Valid @RequestBody OrderDTO orderDTO, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();

            return ResponseEntity.badRequest().body(apiResponse);
        }
        OrderEntity orderEntity = orderService.saveOrder(orderDTO);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(orderEntity)
                .message("Insert sucessfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
