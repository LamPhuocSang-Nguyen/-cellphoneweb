package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.TokenDTO;
import com.example.cellphoneweb.jwt.JwtHelper;
import com.example.cellphoneweb.models.TokenEntity;
import com.example.cellphoneweb.models.UserEntity;
import com.example.cellphoneweb.repositorise.RoleRepository;
import com.example.cellphoneweb.repositorise.TokenRepository;
import com.example.cellphoneweb.repositorise.UserRepository;
import com.example.cellphoneweb.responses.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImp implements ILoginServiceImp{
    private final Gson gson = new Gson();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity checkLogin(String username, String password) {

        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity != null) {

            //kiểm tra password trong database có match với password user truyền lên hay không
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                System.out.println(userEntity.getUsername());
                return userEntity;
            }
        }
        return null;
    }

    @Override
    public TokenDTO refreshLogin(String refreshToken) {
        // Check if the refresh token is expired
        if (jwtHelper.isRefreshTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh token is expired.");
        }

        // Decode the refresh token to extract the username
        Claims claims = jwtHelper.decodeRefreshToken(refreshToken);
        String username = claims.getSubject();

        // Retrieve user details and role
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found.");
        }
        String role = user.getRole().getName();

        // Create a new access token
        String jwtToken = jwtHelper.generateToken(role);


        // Save the new access token to the TokenTable (use your actual TokenRepository to save)
        TokenEntity tokenEntity = tokenRepository.findByUserId(user.getId());
        tokenEntity.setAcessToken(jwtToken);
        tokenEntity.setRefresToken(refreshToken);
        tokenRepository.save(tokenEntity);

        // Create a TokenDTO to return
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(jwtToken);
        tokenDTO.setRefreshToken(refreshToken);
        // Optional if you want to return the new refresh token

        return tokenDTO;
    }


    public ApiResponse loginWithUserNameAndPassword(String username, String password) throws JsonProcessingException {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(token);

            String json = gson.toJson(authentication.getAuthorities());
            String jwtToken = jwtHelper.generateToken(json);
            String jwtTokenRefresh = jwtHelper.generateRefreshToken(username);

            UserEntity userEntity = checkLogin(username, password);
            if (userEntity != null) {
                // Check if the user already has a token record
                TokenEntity tokenEntity = tokenRepository.findByUserId(userEntity.getId());

                if (tokenEntity != null) {
                    // Update existing token
                    tokenEntity.setAcessToken(jwtToken);
                    tokenEntity.setRefresToken(jwtTokenRefresh);
                } else {
                    // Create a new token record if it doesn't exist
                    tokenEntity = new TokenEntity();
                    tokenEntity.setAcessToken(jwtToken);
                    tokenEntity.setRefresToken(jwtTokenRefresh);
                    tokenEntity.setUser(userEntity);
                }

                // Save the token entity (will update if it already exists)
                tokenRepository.save(tokenEntity);

                TokenDTO tokenDTO = TokenDTO.builder()
                        .accessToken(tokenEntity.getAcessToken())
                        .refreshToken(tokenEntity.getRefresToken())
                        .build();

                return ApiResponse.builder()
                        .message("OKOK")
                        .data(tokenDTO)
                        .build();
            } else {
                return ApiResponse.builder()
                        .message("Login Fail")
                        .data("Invalid username or password")
                        .build();
            }
        } catch (UsernameNotFoundException e) {
            return ApiResponse.builder()
                    .message("Login Fail")
                    .data("Co loi :" + e.getMessage())
                    .build();
        } catch (BadCredentialsException e) {
            return ApiResponse.builder()
                    .message("Login Fail")
                    .data("User is banned")
                    .build();
        }
    }


}
