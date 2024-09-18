package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.UserDTO;
import com.example.cellphoneweb.models.User;
import com.example.cellphoneweb.repositorise.UserRepository;
import com.example.cellphoneweb.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        User user = User.builder()
                .user_name(userDTO.getUser_name())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .address(userDTO.getAddress())
                .phone(userDTO.getPhone())
                .build();
        return userRepository.save(user);
    }
    @Override
    public List<UserResponse> findUserByEmail(String email){
        return userRepository.findByEmail(email).stream()
                .map(UserResponse::fromUser)
                .toList();
    }

    @Override
    public User updateUser(int User_id,UserDTO userDTO) {
        User user = userRepository.findById(User_id);
        if(user != null){
            user.setUser_name(userDTO.getUser_name());
            user.setPassword(userDTO.getPassword());
            user.setEmail(userDTO.getEmail());
            user.setAddress(userDTO.getAddress());
            user.setPhone(userDTO.getPhone());
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User deleteUser(int user_Id) {
        User user = userRepository.findById(user_Id);
        userRepository.delete(user);
        return user;
    }

    @Override
    public User getUser(int user_Id) {
        return userRepository.findById(user_Id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(User ->{
            return UserResponse.fromUser(User);
        });
    }
}
