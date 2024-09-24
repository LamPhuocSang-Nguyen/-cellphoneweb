package com.example.cellphoneweb.controllers;

import com.example.cellphoneweb.dtos.OrderDTO;
import com.example.cellphoneweb.dtos.OrderDetailDTO;
import com.example.cellphoneweb.dtos.OrderRequestDTO;
import com.example.cellphoneweb.exceptions.ResourceNotFoundException;
import com.example.cellphoneweb.models.OrderDetailEntity;
import com.example.cellphoneweb.models.OrderEntity;
import com.example.cellphoneweb.responses.ApiResponse;
import com.example.cellphoneweb.responses.OrderListResponse;
import com.example.cellphoneweb.responses.OrderResponse;
import com.example.cellphoneweb.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
        Page<OrderEntity> orderRespones = orderService.getOrders(pageable);
        int totalPage = orderRespones.getTotalPages();
        List<OrderResponse> responseList = orderRespones.getContent().stream()
                .map(order -> OrderResponse.fromOrder(order))
                .toList();

        OrderListResponse orderListResponse = OrderListResponse.builder()
                .orderListResponses(responseList)
                .totalPages(totalPage)
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Show orders sucessfully")
                .data(orderListResponse) // List of students
                .build();

        return ResponseEntity.ok(apiResponse);
    }

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
    public ResponseEntity<ApiResponse> addOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO, BindingResult result){
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
        OrderDTO orderDTO = orderRequestDTO.getOrderDTO();
        List<Long> productIds = orderRequestDTO.getProductIds();
        List<Integer> quantities = orderRequestDTO.getQuantities();
        List<String> colors = orderRequestDTO.getColors();

        OrderEntity orderEntity = orderService.saveOrder(orderDTO, productIds, quantities, colors);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(orderEntity)
                .message("Insert successfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(apiResponse);
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse> editOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO, BindingResult result) {
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
            .map(FieldError::getDefaultMessage).toList();
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .message("Validation faild")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();

            return ResponseEntity.badRequest().body(apiResponse);
        }
        OrderEntity orderEntity = orderService.updateOrder(id, orderDTO);
        if(orderEntity == null){
            throw new ResourceNotFoundException("order khong tim thay voi id " + id);
        }

        ApiResponse apiResponse = ApiResponse.builder()
                    .data(orderEntity)
                    .message("Update sucessfully")
                    .status(HttpStatus.OK.value())
                    .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long id){
        OrderEntity orderEntity = orderService.getOrderById(id);
        if(orderEntity == null){
            throw new ResourceNotFoundException("Order khong tim thay voi id "+ id);
        }
        orderService.deleteOrder(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(id)
                .message("Delete sucessfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/details/{orderDetailId}")
    public ResponseEntity<ApiResponse> updateOrderDetail(
            @PathVariable long orderDetailId,
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();

            return ResponseEntity.badRequest().body(apiResponse);
        }

        OrderDetailEntity existingOrderDetail = orderService.getOrderDetailById(orderDetailId);
        if (orderDetailDTO.getColor() != null) {
            existingOrderDetail.setColor(orderDetailDTO.getColor());
        }
        OrderDetailEntity updatedOrderDetail = orderService.updateOrderDetail(orderDetailId, orderDetailDTO);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(updatedOrderDetail)
                .message("Update sucessfully")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
