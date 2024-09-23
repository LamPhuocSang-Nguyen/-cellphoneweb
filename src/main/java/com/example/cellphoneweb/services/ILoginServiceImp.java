package com.example.cellphoneweb.services;

import com.example.cellphoneweb.dtos.TokenDTO;
import com.example.cellphoneweb.models.UserEntity;

public interface ILoginServiceImp {

    UserEntity checkLogin(String username, String password);

    TokenDTO refreshLogin(String refreshToken);
}
