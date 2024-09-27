package com.example.cellphoneweb.configurations;

import com.example.cellphoneweb.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Security {

    @Autowired
    private CustomAuthenProvider customAuthenProvider;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private CorsFilter corsFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                .cors(Customizer.withDefaults())
                .addFilterAfter(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login/**","/user/register").permitAll()
                                .requestMatchers("/swagger-ui/**", "/v3/**").permitAll()
                                .requestMatchers("/user/register").hasAnyRole("USER")
                                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                                .requestMatchers("/api/v1/order/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/product/**").permitAll()
                                .requestMatchers("/admin/categories/**").hasRole("ADMIN")
                                .requestMatchers("/admin/categories").permitAll()
<<<<<<< HEAD
                                .requestMatchers("/api/v1/admin/product/image/uploads/**").hasRole("ADMIN")
=======
                                .requestMatchers("/user/getAllUser").permitAll()
                                .requestMatchers("/api/v1/admin/categories").permitAll()
                                .requestMatchers("/api/v1/admin/categories/update/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/admin/categories/delete/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/admin/vouchers").hasRole("ADMIN")
                                .requestMatchers("/api/v1/admin/vouchers/code/**").permitAll()
                                .requestMatchers("/api/v1/product/getAll").permitAll()
>>>>>>> fabe2edc3650f8aceb231a2ca2dc72c8bec226c7
                                .anyRequest().authenticated()


                )
//                .httpBasic(withDefaults());
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();

    }
}
