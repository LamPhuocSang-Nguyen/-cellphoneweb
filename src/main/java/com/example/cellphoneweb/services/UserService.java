package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.UserDTO;
import com.example.cellphoneweb.models.UserEntity;
import com.example.cellphoneweb.repositorise.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity register(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .isActive(true)
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .build();
        return userRepository.save(userEntity);
    }
}
