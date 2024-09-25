package com.example.cellphoneweb.configurations;
import com.example.cellphoneweb.models.UserEntity;
import com.example.cellphoneweb.services.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomAuthenProvider implements AuthenticationProvider {
    @Autowired
    private LoginServiceImp loginServiceImp;

//    @Autowired
//    private RoleRepository roleRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        System.out.println("Username: " + username);
        String password = (String) authentication.getCredentials();
        System.out.println("Pass: " + password);

        if (password == null) {
            throw new BadCredentialsException("Password cannot be null");
        }

        UserEntity userEntity = loginServiceImp.checkLogin(username, password);

        if (userEntity != null) {
            if (!userEntity.getIsActive()) {
                throw new BadCredentialsException("User is banned");
            }
            List<GrantedAuthority> listRoles = new ArrayList<>();
            SimpleGrantedAuthority role = new SimpleGrantedAuthority(userEntity.getRole());
            listRoles.add(role);

            return new UsernamePasswordAuthenticationToken(username, password, listRoles);
        }

        throw new UsernameNotFoundException("Invalid username or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
