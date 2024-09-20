package com.example.cellphoneweb.controllers;

import com.example.cellphoneweb.dtos.RoleDTO;
import com.example.cellphoneweb.models.RoleEntity;
import com.example.cellphoneweb.responses.ApiResponse;
import com.example.cellphoneweb.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/admin/roles")
    public ResponseEntity<ApiResponse> getAllRoles() {
        List<RoleEntity> roles = roleService.getAllRoles();
        if(roles.isEmpty()) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Roles not found")
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(roles)
                .status(HttpStatus.OK.value())
                .message("List Roles found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/admin/roles/{role_Id}")
    public ResponseEntity<ApiResponse> getRoleById(@PathVariable long role_Id) {
        RoleEntity role = roleService.getRole(role_Id);
        if (role == null) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Role not found with ID: " + role_Id)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(role)
                .status(HttpStatus.OK.value())
                .message("Role found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/admin/roles/name/{name}")
    public ResponseEntity<ApiResponse> getRoleByName(@PathVariable String name) {
        List<RoleEntity> role = roleService.getRolesByName(name);
        if (role == null) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Role not found with name: " + name)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(role)
                .status(HttpStatus.OK.value())
                .message("Role found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/admin/roles/create")
    public ResponseEntity<ApiResponse> createRole(@Valid @RequestBody RoleDTO role, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
        if(roleService.existsByName(role.getName())) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Role already exists with name: " + role.getName())
                    .status(HttpStatus.CONFLICT.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        }
        RoleEntity roleEntity = roleService.createRole(role);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(roleEntity)
                .status(HttpStatus.CREATED.value())
                .message("Role created successfully")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/admin/roles/update/{role_Id}")
    public ResponseEntity<ApiResponse> updateRole(@PathVariable long role_Id, @Valid @RequestBody RoleDTO roleDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
        RoleEntity role = roleService.updateRole(role_Id, roleDTO);
        if(role == null) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Role not found with ID: " + role_Id)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(role)
                .status(HttpStatus.OK.value())
                .message("Role updated successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/admin/roles/delete/{role_Id}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable long role_Id) {
        RoleEntity role = roleService.deleteRole(role_Id);
        if(role == null) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data("Role not found with ID: " + role_Id)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Error")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        roleService.deleteRole(role_Id);
        ApiResponse apiResponse = ApiResponse.builder()
                .data("Role deleted successfully")
                .status(HttpStatus.OK.value())
                .message("Role deleted successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
