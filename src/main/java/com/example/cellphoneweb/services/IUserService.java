package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.UserDTO;
import com.example.cellphoneweb.models.UserEntity;

import java.util.List;

public interface IUserService {
    UserEntity register(UserDTO userDTO);

    UserEntity findByUsername(String username);
    List<UserEntity> getAllUsers();
}
