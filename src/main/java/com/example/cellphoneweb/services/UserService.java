package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.UserDTO;
import com.example.cellphoneweb.models.UserEntity;
//import com.example.cellphoneweb.repositorise.RoleRepository;
import com.example.cellphoneweb.repositorise.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserEntity register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

//        Role roleEntity = roleRepository.findByName("ROLE_USER");
//        if (roleEntity == null) {
//            roleEntity = new RoleEntity();
//            roleEntity.setName("ROLE_USER");
//            roleRepository.save(roleEntity);
//        }

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        UserEntity userEntity = UserEntity.builder()
                .username(userDTO.getUsername())
                .password(encodedPassword)
                .address(userDTO.getAddress())
                .isActive(true)
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .role(userDTO.getRole())
                .build();

        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void activate(String username){
        UserEntity user = userRepository.findByUsername(username);
        user.setIsActive(true);
        userRepository.save(user);
    }

//    @Override
//    public User getUserDetailsFromToken(String token) throws RuntimeException {
//        if(jwtTokenUtil.isTokenExpired(token)) {
//            throw new ExpiredTokenException("Token is expired");
//        }
//        String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
//        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
//
//        if (user.isPresent()) {
//            return user.get();
//        } else {
//            throw new Exception("User not found");
//        }
//    }
}
