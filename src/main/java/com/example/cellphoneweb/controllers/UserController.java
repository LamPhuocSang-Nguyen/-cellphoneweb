package com.example.cellphoneweb.controllers;

import com.example.cellphoneweb.dtos.UserDTO;
import com.example.cellphoneweb.models.User;
import com.example.cellphoneweb.responses.ApiReponse;
import com.example.cellphoneweb.responses.UserListResponse;
import com.example.cellphoneweb.responses.UserResponse;
import com.example.cellphoneweb.services.UserService;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class UserController {
    private final UserService userService;
    //get all users
    @GetMapping("/admin/users")
    public ResponseEntity<ApiReponse> getAllUsers() {
    List<User> users = userService.getAllUsers();
    ApiReponse apiReponse = ApiReponse.builder()
            .data(users)
            .status(HttpStatus.OK.value())
            .message("List Students found successfully")
            .build();
    return ResponseEntity.status(HttpStatus.OK).body(apiReponse);
    }
    // get single user by id
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiReponse> getUserById(@PathVariable int id) {
        User user = userService.getUser(id);
        if (user == null) {
            ApiReponse apiReponse = ApiReponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("User Id not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiReponse);
        }

        ApiReponse apiReponse = ApiReponse.builder()
                .data(user)
                .status(HttpStatus.OK.value())
                .message("User found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiReponse);
    }
    @PostMapping("/admin/users/create")
    public ResponseEntity<ApiReponse> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            ApiReponse apiReponse = ApiReponse.builder()
                    .data(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("User not created")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiReponse);
        }
        User createdUser = userService.createUser(userDTO);
        ApiReponse apiReponse = ApiReponse.builder()
                .data(createdUser)
                .status(HttpStatus.CREATED.value())
                .message("User created successfully")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiReponse);
    }
    @PutMapping("/admin/users/update/{id}")
    public ResponseEntity<ApiReponse> updateUser(@PathVariable int id, @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        User user = userService.getUser(id);
        if(user == null){
            ApiReponse apiReponse = ApiReponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("User Id not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiReponse);
        }
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            ApiReponse apiReponse = ApiReponse.builder()
                    .data(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("User not updated")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiReponse);
        }
        User updatedUser = userService.updateUser(id, userDTO);
        ApiReponse apiReponse = ApiReponse.builder()
                .data(updatedUser)
                .status(HttpStatus.OK.value())
                .message("User updated successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiReponse);
    }
    @DeleteMapping("/admin/users/delete/{id}")
    public ResponseEntity<ApiReponse> deleteUser(@PathVariable int id) {
        User user = userService.getUser(id);
        if(user == null){
            ApiReponse apiReponse = ApiReponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("User Id not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiReponse);
        }
        User deletedUser = userService.deleteUser(id);
        ApiReponse apiReponse = ApiReponse.builder()
                .data(deletedUser)
                .status(HttpStatus.OK.value())
                .message("User deleted successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiReponse);
    }
    @GetMapping("/admin/users/page")
    public ResponseEntity<ApiReponse> getAllUsersPage(@RequestParam(value = "page",defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> usersResponse = userService.getAllUsers(pageable);
        int totalPages = usersResponse.getTotalPages();
        List<UserResponse> responseList = usersResponse.getContent();

        UserListResponse userPage = UserListResponse.builder()
                .userResponseList(responseList)
                .totalPages(totalPages)
                .build();

        ApiReponse apiReponse = ApiReponse.builder()
                .data(userPage)
                .status(HttpStatus.OK.value())
                .message("List Users found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiReponse);
    }
    // find user by email
    @GetMapping("/admin/users/email")
    public ResponseEntity<ApiReponse> findUserByEmail(@RequestParam String email) {
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(ApiReponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Search term must not be empty")
                    .build());
        }

        List<UserResponse> users = userService.findUserByEmail(email);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiReponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("User not found")
                    .build());
        }

        ApiReponse apiReponse = ApiReponse.builder()
                .data(users)
                .status(HttpStatus.OK.value())
                .message("User found successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiReponse);
    }
}
