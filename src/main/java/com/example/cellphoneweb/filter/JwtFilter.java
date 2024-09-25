package com.example.cellphoneweb.filter;

import com.example.cellphoneweb.exceptions.TokenExpiredException;
import com.example.cellphoneweb.jwt.JwtHelper;
import com.example.cellphoneweb.responses.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtHelper jwtHelper;

    private final Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> bearerToken = Optional.ofNullable(request.getHeader("Authorization"));

        if (bearerToken.isPresent() && bearerToken.get().startsWith("Bearer ")) {
            String token = bearerToken.get().substring(7);
            System.out.println(token);

            if (!token.isEmpty()) {
                try {
                    Claims claims = jwtHelper.decodeToken(token);
                    String role = claims.getSubject(); // This is your single role
                    Date expirationDate = claims.getExpiration();
                    System.out.println(role);

                    // Create a list of GrantedAuthority from the single role
//                    List<GrantedAuthority> listRoles = new ArrayList<>();

                    Type listType = new TypeToken<ArrayList<SimpleGrantedAuthority>>(){}.getType();
                    List<GrantedAuthority> listRoles = gson.fromJson(role, listType);
                    if (role != null && !role.isEmpty()) {
                        listRoles.add(new SimpleGrantedAuthority(role));
                    }

                    if (!listRoles.isEmpty()) {
                        // Set up the security context
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(null, null, listRoles);
                        SecurityContext securityContext = SecurityContextHolder.getContext();
                        securityContext.setAuthentication(authenticationToken);
                    }
                } catch (ExpiredJwtException e) {
                    ApiResponse apiResponse = ApiResponse.builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message("Token has expired")
                            .data(null)
                            .build();

                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                    response.getWriter().write(new Gson().toJson(apiResponse));
                    return; // Stop further processing
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                    ApiResponse apiResponse = ApiResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("An error occurred while processing the token: " + e.getMessage())
                            .data(null)
                            .build();

                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    response.setContentType("application/json");
                    response.getWriter().write(new Gson().toJson(apiResponse));
                    return; // Stop further processing
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
