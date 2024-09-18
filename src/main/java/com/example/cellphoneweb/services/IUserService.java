package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.UserDTO;
import com.example.cellphoneweb.models.User;
import com.example.cellphoneweb.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    User createUser(UserDTO userDTO);
    User updateUser(int user_Id,UserDTO userDTO);
    User deleteUser(int user_Id);
    User getUser(int user_Id);
    List<User> getAllUsers();
    List<UserResponse> findUserByEmail(String email);
    Page<UserResponse> getAllUsers(Pageable pageable);
}
